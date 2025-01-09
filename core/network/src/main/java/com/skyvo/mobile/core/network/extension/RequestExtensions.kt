package com.skyvo.mobile.core.network.extension

import com.skyvo.mobile.core.network.builder.GlobalRequest

fun <T> T.toRequest(): GlobalRequest<T> {
    val request = GlobalRequest<T>()
    request.content = this
    return request
}