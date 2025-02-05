package com.skyvo.mobile.core.database.localization

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skyvo.mobile.core.database.RoomDatabaseConstant

@Entity(tableName = RoomDatabaseConstant.LOCALIZATION_TABLE)
data class LocalizationEntity(
    @PrimaryKey
    val key: String,
    val value: String
)