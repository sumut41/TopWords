package com.skyvo.mobile.core.database.localization

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localizations")
data class LocalizationEntity(
    @PrimaryKey
    val key: String,
    val value: String
)