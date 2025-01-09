package com.skyvo.mobile.core.base.viewmodel.error

enum class ErrorTypeEnum(val type: Int) {
    UNDEFINED(0),
    GENERIC(1),
    FRIENDLY(2),
    AUTHORIZATION(3),
    VALIDATION(4),
    MAX_REQUEST_EXCEED(5),
    FORCE_UPDATE(6),
    STORE_SERVICE_PROVIDER(7)
}