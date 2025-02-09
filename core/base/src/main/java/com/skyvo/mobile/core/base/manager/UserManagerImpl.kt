package com.skyvo.mobile.core.base.manager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE
import com.skyvo.mobile.core.shared.extension.get
import com.skyvo.mobile.core.shared.extension.set
import java.util.Calendar
import javax.inject.Inject

class UserManagerImpl @Inject constructor(
    private val context: Context
): UserManager {

    private var userPrefs: SharedPreferences
    private lateinit var masterKey: MasterKey

    init {
        if (isTestRunner()) {
            userPrefs = context.getSharedPreferences(KEY_TEST_USER_PREFS, MODE_PRIVATE)
        } else {
            try {
                masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()
            } catch (e: Exception) {
                val spec = KeyGenParameterSpec.Builder(
                    MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                    .build()
                masterKey = MasterKey.Builder(context)
                    .setKeyGenParameterSpec(spec)
                    .build()
            }

            userPrefs = EncryptedSharedPreferences.create(
                context,
                USER_PREFS,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    private fun isTestRunner(): Boolean {
        return Build.FINGERPRINT.lowercase() == ROBOLECTRIC
    }

    override var isDarkTheme: Boolean
        get() = userPrefs.get(KEY_IS_DARK_THEME) ?: false
        set(value) {
            userPrefs.set(KEY_IS_DARK_THEME, value)
        }

    override var learnLanguage: Language?
        get() = userPrefs.get(KEY_LEARN_LANGUAGE_CODE)
        set(value) {
            userPrefs.set(KEY_LEARN_LANGUAGE_CODE, value)
        }

    override var nativeLanguage: Language?
        get() = userPrefs.get(KEY_DEFAULT_LANGUAGE_CODE)
        set(value) {
            userPrefs.set(KEY_DEFAULT_LANGUAGE_CODE, value)
        }

    override var customerLevel: Level?
        get() = userPrefs.get(KEY_LEVEL_CODE)
        set(value) {
            userPrefs.set(KEY_LEVEL_CODE, value)
        }

    override var goalMinute: Int?
        get() = userPrefs.get(KEY_GOAL_MINUTE_CODE)
        set(value) {
            userPrefs.set(KEY_GOAL_MINUTE_CODE, value)
        }

    override var isCompletedSetup: Boolean
        get() = userPrefs.get(KEY_IS_COMPLETED_SETUP) ?: false
        set(value) {
            userPrefs.set(KEY_IS_COMPLETED_SETUP, value)
        }

    override fun checkAndUpdateWeeklyAttendance() {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_WEEK)
        val currentTime = calendar.timeInMillis

        // Pazartesi için kontrol (Calendar.MONDAY = 2)
        if (today == Calendar.MONDAY) {
            val lastMonday = userPrefs.getLastMonday()
            val oneWeek = 7 * 24 * 60 * 60 * 1000L // 1 hafta milisaniye cinsinden

            // Eğer son pazartesiden bir haftadan fazla geçmişse veya ilk kez gelindiyse
            if (currentTime - lastMonday > oneWeek || lastMonday == 0L) {
                userPrefs.clearWeeklyAttendance()
                userPrefs.saveLastMonday(currentTime)
            }
        }

        // Bugünün katılımını kaydet
        userPrefs.saveWeeklyAttendance(today)
    }

    override fun getWeeklyAttendance(): Set<Int> {
        return userPrefs.getWeeklyAttendance()
    }

    private fun SharedPreferences.saveWeeklyAttendance(dayOfWeek: Int) {
        val currentSet = getStringSet(WEEKLY_ATTENDANCE_KEY, emptySet()) ?: emptySet()
        val newSet = currentSet.toMutableSet()
        newSet.add(dayOfWeek.toString())
        edit().putStringSet(WEEKLY_ATTENDANCE_KEY, newSet).apply()
    }

    fun SharedPreferences.getWeeklyAttendance(): Set<Int> {
        return getStringSet(WEEKLY_ATTENDANCE_KEY, emptySet())?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
    }

    private fun SharedPreferences.saveLastMonday(timestamp: Long) {
        edit().putLong(LAST_MONDAY_KEY, timestamp).apply()
    }

    private fun SharedPreferences.getLastMonday(): Long {
        return getLong(LAST_MONDAY_KEY, 0)
    }

    private fun SharedPreferences.clearWeeklyAttendance() {
        edit().remove(WEEKLY_ATTENDANCE_KEY).apply()
    }

    companion object {
        private const val WEEKLY_ATTENDANCE_KEY = "weekly_attendance"
        private const val LAST_MONDAY_KEY = "last_monday_date"
        private const val ROBOLECTRIC = "robolectric"
        private const val USER_PREFS = "encrypted_userh_prefs"
        private const val KEY_TEST_USER_PREFS = "test_user_prefs"
        private const val KEY_IS_DARK_THEME = "key_is_dark_theme"
        private const val KEY_LEARN_LANGUAGE_CODE = "key_learn_language_code"
        private const val KEY_DEFAULT_LANGUAGE_CODE = "key_default_language_code"
        private const val KEY_LEVEL_CODE = "key_choose_level_list"
        private const val KEY_GOAL_MINUTE_CODE = "key_goal_level_minute_code"
        private const val KEY_IS_COMPLETED_SETUP = "key_is_completed_setup_key"
    }
}