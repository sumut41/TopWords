package com.skyvo.mobile.core.shared.exception

import java.io.IOException

sealed class NetworkException: IOException() {
    data object NoInternetConnection: NetworkException() {
        private fun readResolve(): Any = NoInternetConnection
    }

    data object GenericError: NetworkException() {
        private fun readResolve(): Any = GenericError
    }

    data object UnknownError: NetworkException() {
        private fun readResolve(): Any = UnknownError
    }

    data object TimeOutError: NetworkException() {
        private fun readResolve(): Any = TimeOutError
    }

    data object SSLHandshakeError: NetworkException() {
        private fun readResolve(): Any = SSLHandshakeError
    }

    data class ParsingJsonError(val errorMessage: String?): NetworkException()

    data class ResultError(val type: Int?, val errorMessage: String?, val errorTitle: String?): NetworkException()
}