package com.skyvo.mobile.core.shared.extension

import android.content.Context
import android.content.Intent

fun shareApp(context: Context) {
    val packageName = context.packageName
    val shareText = "Check out this app: https://play.google.com/store/apps/details?id=$packageName"

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(intent, "Share via"))
}