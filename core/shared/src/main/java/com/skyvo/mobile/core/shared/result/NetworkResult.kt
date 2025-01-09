package com.skyvo.mobile.core.shared.result

import com.skyvo.mobile.core.shared.exception.NetworkException

sealed class NetworkResult<T>(
    val data: T? = null,
    val error: NetworkException? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)

    class Error<T>(error: NetworkException, data: T? = null) : NetworkResult<T>(data, error)

    class Loading<T>(data: T? = null) : NetworkResult<T>(data)

    class Complete<T>(data: T? = null) : NetworkResult<T>(data)

    val dataOrNull: T?
        inline get() = if (this is Success) data else null
}