package com.skyvo.mobile.core.base.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.skyvo.mobile.core.shared.exception.ExceptionFBHelper
import javax.inject.Inject

class FirebaseAnalyticsHelper @Inject constructor(
    private val context: Context
) {
    @SuppressLint("MissingPermission")
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logEvent(eventName: String, params: Bundle) {
        firebaseAnalytics.logEvent(eventName,params)
    }

    fun logScreenView(screenName: String, screenClass: String) {
        try {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
            }
        } catch (ex: Exception) {
            ExceptionFBHelper.recordException(ex)
        }
    }
}