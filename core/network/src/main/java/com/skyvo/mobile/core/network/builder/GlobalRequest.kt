package com.skyvo.mobile.core.network.builder

import com.google.gson.annotations.SerializedName

data class GlobalRequest<T>(
    @SerializedName("Content")
    var content: T? = null,
    @SerializedName("OnCallPhone")
    var onCallPhone: Boolean = false,
    @SerializedName("ClassName")
    var className: String = "",
)