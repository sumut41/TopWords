package com.skyvo.mobile.core.shared.extension

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skyvo.mobile.core.shared.BuildConfig

inline fun <reified T> SharedPreferences.get(key: String): T? {
    if (!this.contains(key)) {
        return null
    }

    return when(T::class) {
        String::class -> this.getString(key, null) as T
        Int::class -> this.getInt(key, 0) as T
        Long::class -> this.getLong(key, 0L) as T
        Float::class -> this.getFloat(key, 0f) as T
        Boolean::class -> this.getBoolean(key, false) as T
        else -> {
            Gson().fromJson(getString(key, ""), T::class.java)
        }
    }
}

inline fun <reified T> SharedPreferences.set(key: String, value: T?) {
    val editor = this.edit()

    if (value == null) {
        editor.remove(key)
        editor.apply()
    } else {
        when(T::class) {
            String::class -> editor.putString(key, value as String)
            Int::class -> editor.putInt(key, value as Int)
            Long::class -> editor.putLong(key, value as Long)
            Float::class -> editor.putFloat(key, value as Float)
            Boolean::class -> editor.putBoolean(key, value as Boolean)
            else -> {
                editor.putString(key, Gson().toJson(value))
            }
        }
    }
    editor.commit()
    editor.apply()
}

fun isTestSharedRunner(): Boolean = BuildConfig.IS_DEBUG

inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when(T::class) {
        String::class -> this.getString(key, defaultValue as? String) as T
        Int::class -> this.getInt(key, defaultValue as? Int ?: -1) as T
        Long::class -> this.getLong(key, defaultValue as? Long ?: -1L) as T
        Float::class -> this.getFloat(key, defaultValue as? Float ?: -1f) as T
        Boolean::class -> this.getBoolean(key, defaultValue as? Boolean == true) as T
        else -> {
            val type = object : TypeToken<T>() {}.type
            val json = this.getString(key, null)
            Gson().fromJson(json, type)
        }
    }
}

inline fun <reified T> SharedPreferences.put(key: String, value: T) {
    val editor = this.edit()
    value?.let {
        when(T::class) {
            String::class -> editor.putString(key, value as String)
            Int::class -> editor.putInt(key, value as Int)
            Long::class -> editor.putLong(key, value as Long)
            Float::class -> editor.putFloat(key, value as Float)
            Boolean::class -> editor.putBoolean(key, value as Boolean)
            else -> {
                val json = it.run { Gson().toJson(this) }
                editor.putString(key, json)
            }
        }
    }

    editor.apply()
}

fun SharedPreferences.delete(key: String) {
    this.edit().remove(key).apply()
}