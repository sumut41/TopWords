package com.skyvo.mobile.core.network.util

class NetworkServiceConstant {

    object Header {
        const val ContentType = "Content-Type"
        const val ClientOSVersion = "Client-OS-Version"
        const val CorrelationId = "Correlation-Id"
        const val UnlessID = "Unless-ID"
        const val Ydry = "Ydry"
        const val YdryId = "Ydry-Id"
    }

    object Url {
        const val DELPHI_URL_EXTENSION = "delphi"
        const val LOCATOR_URL_EXTENSION = "locator"
    }

    object Status {
        const val SERVICE_CALL_SUCCESS = "success"
        const val ERROR_CONTENT = "errorContent"
        const val TEST_AUTO_FLAG = "1"
    }
}