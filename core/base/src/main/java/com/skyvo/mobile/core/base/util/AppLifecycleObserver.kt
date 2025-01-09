package com.skyvo.mobile.core.base.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class AppLifecycleObserver(private val onAction: (String) -> Unit): DefaultLifecycleObserver {

    private var backgroundTime: Long = 0

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        backgroundTime = System.currentTimeMillis()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        val foregroundTime = System.currentTimeMillis()
        val elapsedTime = foregroundTime - backgroundTime
        if (elapsedTime > 10 * 60 * 1000 && backgroundTime != 0L) {
            onAction(AppLifecycleClientAction.TIME_OUT)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        onAction(AppLifecycleClientAction.DESTROY)
        super.onDestroy(owner)
    }
}