package com.skyvo.mobile.core.network.builder

data class BaseExceptionModel(
    var type: Int = 1,
    var message: String? = null,
    var title: String? = null
)