package com.skyvo.mobile.core.base.application

import android.content.Context

abstract class MobileCoreApplication: BaseApplication() {

    companion object {
        fun getApplication() : MobileCoreApplication =
            BaseApplication.getApplication() as MobileCoreApplication
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
    }
}