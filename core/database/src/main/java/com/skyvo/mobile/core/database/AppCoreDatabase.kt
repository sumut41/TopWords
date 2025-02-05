package com.skyvo.mobile.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skyvo.mobile.core.database.localization.LocalizationDao
import com.skyvo.mobile.core.database.localization.LocalizationEntity
import com.skyvo.mobile.core.database.word.WordDao
import com.skyvo.mobile.core.database.word.WordEntity

@Database(
    entities = [
        LocalizationEntity::class,
        WordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppCoreDatabase : RoomDatabase() {
    abstract fun localizationDao(): LocalizationDao
    abstract fun wordDao(): WordDao
}