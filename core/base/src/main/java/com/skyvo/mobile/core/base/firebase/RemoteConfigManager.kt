package com.skyvo.mobile.core.base.firebase

interface RemoteConfigManager {

    fun initRemoteConfig(
        minimumFetchIntervalInSeconds: Long = 3600,
        onComplete: ((result: Boolean) -> Unit)?
    )

    fun getString(key: String): String

    fun getBoolean(key: String): Boolean
}