package com.skyvo.mobile.core.base.util

import android.content.Context
import com.skyvo.mobile.core.shared.exception.ExceptionFBHelper
import java.util.Locale

fun Context.forceConfiguration(languageCode: String) {
    try {
        val locale = Locale(languageCode)
        val config = this.resources.configuration
        config.setLocale(locale)
        this.resources.updateConfiguration(
            config,
            this.resources.displayMetrics
        )
    } catch (ex: Exception) {
        ExceptionFBHelper.recordException(ex)
    }
}