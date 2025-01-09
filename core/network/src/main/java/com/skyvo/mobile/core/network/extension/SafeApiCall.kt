package com.skyvo.mobile.core.network.extension

import android.util.MalformedJsonException
import com.skyvo.mobile.core.network.builder.GlobalResponse
import com.skyvo.mobile.core.shared.exception.NetworkException
import com.skyvo.mobile.core.shared.result.NetworkResult
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

suspend fun <T : Any> fetch(call: suspend () -> Response<GlobalResponse<T>>) =
    flow {
        try {
            emit(NetworkResult.Loading())
            val apiResponse = call.invoke()
            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()
                if (response != null) {
                    if (response.isSuccess) {
                        emit(NetworkResult.Success(response.content))
                    } else {
                        response.errorContent?.let {
                            emit(
                                NetworkResult.Error(
                                    error = NetworkException.ResultError(
                                        type = it.type,
                                        errorMessage = it.message,
                                        errorTitle = it.title,
                                    ),
                                    data = response.content
                                )
                            )
                        } ?: run {
                            emit(
                                NetworkResult.Error(
                                    error = NetworkException.GenericError,
                                    data = response.content
                                )
                            )
                        }
                    }
                } else {
                    emit(NetworkResult.Error(NetworkException.GenericError))
                }
            } else {
                emit(NetworkResult.Error(NetworkException.GenericError))
            }
            emit(NetworkResult.Complete())
        } catch (e: Exception) {
            emit(handleException(e))
            emit(NetworkResult.Complete())
        }
    }

private fun <T : Any> handleException(exception: Exception): NetworkResult<T> {
    return when (exception) {
        is UnknownHostException -> {
            NetworkResult.Error(NetworkException.NoInternetConnection)
        }

        is SocketTimeoutException -> {
            NetworkResult.Error(NetworkException.TimeOutError)
        }

        is ConnectException -> {
            NetworkResult.Error(NetworkException.NoInternetConnection)
        }

        is SSLHandshakeException -> {
            NetworkResult.Error(NetworkException.SSLHandshakeError)
        }

        is MalformedJsonException -> {
            NetworkResult.Error(NetworkException.ParsingJsonError(exception.message))
        }

        else -> NetworkResult.Error(NetworkException.UnknownError)
    }
}