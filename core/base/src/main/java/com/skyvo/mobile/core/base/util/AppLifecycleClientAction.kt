package com.skyvo.mobile.core.base.util

import androidx.annotation.StringDef

@StringDef(AppLifecycleClientAction.TIME_OUT, AppLifecycleClientAction.DESTROY)
@Retention(AnnotationRetention.SOURCE)
annotation class AppLifecycleClientAction {
    companion object {
        const val TIME_OUT = "time_out"
        const val DESTROY = "destroy"
    }
}