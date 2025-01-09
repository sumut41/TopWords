package com.skyvo.mobile.core.network.di

import android.content.Context
import com.skyvo.mobile.core.network.cokkie.CookieManager
import com.skyvo.mobile.core.network.util.DeviceManager
import com.skyvo.mobile.core.network.util.DeviceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitDomain(
        @ApplicationContext context: Context,
        deviceUtil: DeviceManager,
        cookieManager: CookieManager
    ) = RetrofitProvider(
        context,
        deviceUtil,
        cookieManager
    )

    @Singleton
    @Provides
    fun provideDeviceManager(
        @ApplicationContext context: Context
    ): DeviceManager {
        return DeviceManagerImpl(context)
    }

    @Singleton
    @Provides
    fun provideCookieManager(): CookieManager =
        CookieManager()
}