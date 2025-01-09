package com.skyvo.mobile.core.shared.exception

import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class ExceptionFBHelper {
    companion object {
        fun logError(message: String?) {
            if (message != null) {
                FirebaseCrashlytics.getInstance().log(message)
            }
        }

        @JvmStatic
        fun setUserId(userId:String?){
            userId?.let {
                val fbInstance = FirebaseCrashlytics.getInstance()
                fbInstance.setUserId(it)
            }
        }

        @JvmStatic
        fun recordException(ex: Exception, logParams: Map<String, String>? = null) {
            try {
                val fbInstance = FirebaseCrashlytics.getInstance()
                if (logParams != null) {
                    for (entry in logParams) {
                        fbInstance.setCustomKey(entry.key, entry.value)
                    }
                }
                fbInstance.recordException(ex)
            } catch (ex: Exception) {
                Timber.d(ex)
            }
        }
    }
}