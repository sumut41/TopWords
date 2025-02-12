package com.skyvo.mobile.core.resource

import android.content.ContentResolver
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri

class SoundEffect(
    private val context: Context
) {
    fun playSuccess() {
        RingtoneManager.getRingtone(
            context,
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + R.raw.success_sound)
        ).play()
    }

    fun playError() {
        RingtoneManager.getRingtone(
            context,
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + R.raw.error_sound)
        ).play()
    }

    fun playCompleted() {
        RingtoneManager.getRingtone(
            context,
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + R.raw.complete_course_sound)
        ).play()
    }
}