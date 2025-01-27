package com.skyvo.mobile.top.words

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.skyvo.mobile.core.base.application.MobileCoreApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppAndroidApplication: MobileCoreApplication() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
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