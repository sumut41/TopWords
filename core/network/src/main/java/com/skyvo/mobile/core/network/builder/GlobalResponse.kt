package com.skyvo.mobile.core.network.builder

import com.squareup.moshi.Json

data class GlobalResponse<T>(
    val content: T?,
    val errorContent: BaseExceptionModel? = null,
    @Json(name = "success")
    var isSuccess: Boolean = true
)