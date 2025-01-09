package com.skyvo.mobile.core.database.localization

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skyvo.mobile.core.database.RoomDatabaseConstant

@Dao
interface LocalizationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(localizations: List<LocalizationEntity>)

    @Query("SELECT * FROM ${RoomDatabaseConstant.LOCALIZATION_TABLE}")
    suspend fun getAllStringResource(): List<LocalizationEntity>
}