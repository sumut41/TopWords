package com.skyvo.mobile.core.base.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

abstract class BaseApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    init {
        instance = this
    }

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getApplication(): BaseApplication? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        /*
        TODO IS_DEBUG ???
        if (BuildConfig.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        */
    }
}