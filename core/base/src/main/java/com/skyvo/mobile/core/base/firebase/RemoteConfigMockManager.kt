package com.skyvo.mobile.core.base.firebase

class RemoteConfigMockManager : RemoteConfigManager {
    override fun initRemoteConfig(
        minimumFetchIntervalInSeconds: Long,
        onComplete: ((result: Boolean) -> Unit)?
    ) {
        onComplete?.invoke(true)
    }

    override fun getString(key: String): String {
        return ""
    }

    override fun getBoolean(key: String): Boolean {
        return false
    }
}