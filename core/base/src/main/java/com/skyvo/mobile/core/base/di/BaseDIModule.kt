package com.skyvo.mobile.core.base.di

import com.skyvo.mobile.core.base.deeplink.DeeplinkManager
import com.skyvo.mobile.core.base.deeplink.DeeplinkManagerImpl
import com.skyvo.mobile.core.base.firebase.RemoteConfigManager
import com.skyvo.mobile.core.base.firebase.RemoteConfigManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class BaseDIModule {
    @Singleton
    @Provides
    fun provideRemoteConfigManager(): RemoteConfigManager {
        return RemoteConfigManagerImpl()
    }

    @Singleton
    @Provides
    fun provideDeeplinkManager(): DeeplinkManager {
        return DeeplinkManagerImpl()
    }
}