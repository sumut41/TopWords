package com.skyvo.mobile.core.base.di

import android.content.Context
import com.skyvo.mobile.core.base.deeplink.DeeplinkManager
import com.skyvo.mobile.core.base.deeplink.DeeplinkManagerImpl
import com.skyvo.mobile.core.base.firebase.RemoteConfigManager
import com.skyvo.mobile.core.base.firebase.RemoteConfigManagerImpl
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.manager.UserManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideUserManager(
        @ApplicationContext context: Context
    ): UserManager {
        return UserManagerImpl(context)
    }
}