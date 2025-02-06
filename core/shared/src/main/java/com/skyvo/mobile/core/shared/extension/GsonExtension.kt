package com.skyvo.mobile.core.shared.extension

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

val getGson: Gson
    get() {
        val builder = GsonBuilder()
        builder.serializeNulls().setLenient()
        return builder.setPrettyPrinting().create()
    }

inline fun <reified T> T.toEncoded(): String {
    return URLEncoder.encode(this.toJson(), StandardCharsets.UTF_8.name())
}

inline fun <reified T> T.toJson(): String {
    return getGson.toJson(this)
}

inline fun <reified T> String?.toModel(): T? {
    return try {
        if (this != null) {
            Gson().fromJson(this, T::class.java)
        } else {
            null
        }
    } catch (ex: Exception){
        null
    }
}

inline fun <reified T> String?.convertJsonToList(): ArrayList<T>? {
    try {
        if (this != null) {
            val listType = object : TypeToken<ArrayList<T>>() {}.type
            return Gson().fromJson(this, listType)
        } else {
            return null
        }
    } catch (ex: Exception){
        return null
    }
}