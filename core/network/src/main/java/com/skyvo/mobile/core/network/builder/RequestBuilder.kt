package com.skyvo.mobile.core.network.builder

import com.skyvo.mobile.core.network.util.DeviceManager
import com.skyvo.mobile.core.network.util.NetworkServiceConstant
import okhttp3.Request
import java.util.UUID

class RequestBuilder {
    companion object {

        fun build(request: Request, devicePackageManager: DeviceManager): Request.Builder {
            val correlationId = UUID.randomUUID().toString()
            val requestBuilder = createBuilder(request, correlationId, devicePackageManager)
            return requestBuilder
        }

        private fun createBuilder(
            original: Request,
            correlationId: String,
            devicePackageManager: DeviceManager
        ) = original.newBuilder()
            .addHeader(NetworkServiceConstant.Header.ContentType, "application/json")
            .addHeader(
                NetworkServiceConstant.Header.ClientOSVersion,
                devicePackageManager.getOSVersion()
            )
            .addHeader(
                NetworkServiceConstant.Header.CorrelationId, correlationId
            )
    }
}