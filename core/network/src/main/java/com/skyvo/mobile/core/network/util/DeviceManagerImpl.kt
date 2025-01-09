package com.skyvo.mobile.core.network.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.nfc.NfcAdapter
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import java.io.File
import javax.inject.Inject

class DeviceManagerImpl @Inject constructor(
    private val context: Context
) : DeviceManager {

    override fun getApplicationVersionName(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "Unknown"
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown"
        }
    }

    override fun getApplicationVersionCode(): Int {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            1
        }
    }

    override fun getOSVersion(): String {
        return Build.VERSION.RELEASE
    }

    override fun getDeviceName(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }

    override fun getDeviceModel(): String {
        return Build.MODEL
    }

    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun isDeviceRooted(): Boolean {
        val buildTags = Build.TAGS
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true
        }

        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }

        return try {
            Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su")).inputStream.bufferedReader().readLine() != null
        } catch (e: Exception) {
            false
        }
    }

    override fun isEmulator(): Boolean {
        return (
                Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                        Build.FINGERPRINT.startsWith("generic") ||
                        Build.FINGERPRINT.startsWith("unknown") ||
                        Build.HARDWARE.contains("goldfish") ||
                        Build.HARDWARE.contains("ranchu") ||
                        Build.MODEL.contains("google_sdk") ||
                        Build.MODEL.contains("Emulator") ||
                        Build.MODEL.contains("Android SDK built for x86") ||
                        Build.MODEL.contains("VirtualBox") ||
                        Build.MANUFACTURER.contains("Genymotion") ||
                        Build.PRODUCT.contains("sdk_google") ||
                        Build.PRODUCT.contains("google_sdk") ||
                        Build.MODEL.startsWith("sdk_") ||
                        Build.PRODUCT.contains("vbox86p") ||
                        Build.PRODUCT.contains("emulator") ||
                        Build.PRODUCT.contains("simulator")
                )
    }

    override fun networkOperatorName(): String {
        val telephonyManager = ContextCompat.getSystemService(context, TelephonyManager::class.java)
        return telephonyManager?.networkOperatorName.orEmpty()
    }

    @SuppressLint("MissingPermission")
    override fun getConnectionType(): String {
        try {
            val connectivityManager =
                context.getSystemService(
                    Context.CONNECTIVITY_SERVICE,
                ) as? ConnectivityManager ?: return ""

            val capabilities =
                connectivityManager.getNetworkCapabilities(
                    connectivityManager.activeNetwork,
                ) ?: return ""

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "CELLULAR"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> "VPN"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "ETHERNET"
                else -> "OTHER"
            }
        } catch (ex: Exception) {
            return "OTHER"
        }
    }

    @SuppressLint("MissingPermission")
    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        connectivityManager?.let {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } ?: return false
    }

    override fun isNfcEnabled(): Boolean {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter != null) {
            return try {
                nfcAdapter.isEnabled
            } catch (exp: Exception) {
                try {
                    nfcAdapter.isEnabled
                } catch (exp: Exception) {
                    false
                }
            }
        }
        return false
    }

    override fun isDeviceHasNFC(): Boolean {
        if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            return false
        } else {
            val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
            return nfcAdapter != null
        }
    }
}