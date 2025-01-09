package com.skyvo.mobile.core.base.firebase

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import timber.log.Timber

class RemoteConfigManagerImpl : RemoteConfigManager {
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun initRemoteConfig(
        minimumFetchIntervalInSeconds: Long,
        onComplete: ((result: Boolean) -> Unit)?
    ) {
        remoteConfig = Firebase.remoteConfig

        val remoteConfigSettings = remoteConfigSettings {
            setMinimumFetchIntervalInSeconds(minimumFetchIntervalInSeconds)
        }
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { result ->
                onComplete?.invoke(result.isSuccessful)
            }
            .addOnFailureListener { ex ->
                Timber.tag("RemoteConfigError").e(ex.message.orEmpty())
            }
    }

    override fun getString(key: String): String {
        if (!::remoteConfig.isInitialized) {
            remoteConfig = Firebase.remoteConfig
        }
        return remoteConfig.getString(key)
    }

    override fun getBoolean(key: String): Boolean {
        if (!::remoteConfig.isInitialized) {
            remoteConfig = Firebase.remoteConfig
        }
        return remoteConfig.getBoolean(key)
    }
}