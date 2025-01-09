package com.skyvo.mobile.top.words

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.skyvo.mobile.core.base.application.MobileCoreApplication
import com.skyvo.mobile.core.base.firebase.RemoteConfigManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AppAndroidApplication: MobileCoreApplication() {

    @Inject
    lateinit var remoteConfigManager: RemoteConfigManager

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        remoteConfigManager.initRemoteConfig {
            Timber.tag("RemoteConfig").d("Success fetch")
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.default_notification_channel_id),
                getString(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManage = getSystemService(NotificationManager::class.java)
            notificationManage.createNotificationChannel(channel)
        }
    }
}