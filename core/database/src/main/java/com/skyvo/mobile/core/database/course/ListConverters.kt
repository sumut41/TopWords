package com.skyvo.mobile.core.database.course

import androidx.room.TypeConverter

class ListConverters {
    @TypeConverter
    fun fromLongListToString(list: List<Long>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun fromStringToLongList(data: String?): List<Long> {
        return data?.split(",")?.mapNotNull { it.toLongOrNull() } ?: emptyList()
    }

    @TypeConverter
    fun fromStringListToString(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun fromStringToStringList(data: String?): List<String> {
        return data?.split(",")?.map { it.trim() } ?: emptyList()
    }
}