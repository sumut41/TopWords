package com.skyvo.mobile.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skyvo.mobile.core.database.book.BookDao
import com.skyvo.mobile.core.database.book.BookEntity
import com.skyvo.mobile.core.database.course.CourseWordDao
import com.skyvo.mobile.core.database.course.CourseWordEntity
import com.skyvo.mobile.core.database.course.ListConverters
import com.skyvo.mobile.core.database.localization.LocalizationDao
import com.skyvo.mobile.core.database.localization.LocalizationEntity
import com.skyvo.mobile.core.database.word.WordDao
import com.skyvo.mobile.core.database.word.WordEntity

@Database(
    entities = [
        LocalizationEntity::class,
        WordEntity::class,
        CourseWordEntity::class,
        BookEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverters::class)
abstract class AppCoreDatabase : RoomDatabase() {
    abstract fun localizationDao(): LocalizationDao
    abstract fun wordDao(): WordDao
    abstract fun courseDao(): CourseWordDao
    abstract fun bookDao(): BookDao
}