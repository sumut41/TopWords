package com.skyvo.mobile.core.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.skyvo.mobile.core.network.BuildConfig
import com.skyvo.mobile.core.network.builder.RequestBuilder
import com.skyvo.mobile.core.network.cokkie.CookieManager
import com.skyvo.mobile.core.network.util.DeviceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitProvider @Inject constructor(
    private val context: Context,
    deviceUtil: DeviceManager,
    cookieManager: CookieManager
) {

    companion object {
        private const val CONNECTION_TIMEOUT = 60000
        private const val READ_TIMEOUT = 60000
        private const val WRITE_TIMEOUT = 60000
        private const val CERTIFICATE_PATTERN = "*.sada.com" // todo
    }

    private val retrofit: Retrofit by lazy {
        val moshi = Moshi.Builder()
        moshi.add(KotlinJsonAdapterFactory())

        Retrofit.Builder()
            .baseUrl("Base_URL")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi.build()))
            .build()
    }

    private val okHttpClient: OkHttpClient by lazy {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(chuckerInterceptor)
        }
        client.cookieJar(cookieManager)
        client.addInterceptor(httpLoggingInterceptor)
        client.addInterceptor(headerInterceptor)
        client.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        client.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        client.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)

        // client.certificatePinner(certificatePinner)

        return@lazy client.build()
    }

    private val chuckerInterceptor: ChuckerInterceptor by lazy {
        ChuckerInterceptor.Builder(context)
            .collector(
                ChuckerCollector(
                    context = context,
                    showNotification = true,
                    retentionPeriod = RetentionManager.Period.ONE_HOUR
                )
            )
            .maxContentLength(250_000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    private val httpLoggingInterceptor: HttpLoggingInterceptor by lazy {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        return@lazy logging
    }

    private val headerInterceptor: Interceptor by lazy {
        Interceptor {
            val requestBuilder = RequestBuilder.build(it.request(), deviceUtil)
            return@Interceptor it.proceed(requestBuilder.build())
        }
    }

    private val certificatePinner: CertificatePinner by lazy {
        return@lazy CertificatePinner.Builder()
            .add(CERTIFICATE_PATTERN, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
            .build()
    }

    fun <A> create(apiClass: Class<A>): A {
        Timber.tag("Service RetrofitProvider").i("Create: $apiClass")
        return retrofit.create(apiClass)
    }
}