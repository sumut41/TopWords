package com.skyvo.mobile.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skyvo.mobile.core.database.localization.LocalizationDao
import com.skyvo.mobile.core.database.localization.LocalizationEntity
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Database(entities = [LocalizationEntity::class], version = 1)
abstract class AppCoreDatabase : RoomDatabase() {
    abstract fun localizationDao(): LocalizationDao

    @dagger.hilt.InstallIn(SingletonComponent::class)
    @dagger.Module
    object AppDatabaseModule {
        @Singleton
        @Provides
        fun provideDatabase(
            @ApplicationContext context: Context
        ): AppCoreDatabase {
            return Room.databaseBuilder(
                context,
                AppCoreDatabase::class.java,
                "${context.packageName}_${RoomDatabaseConstant.ROOM_DB_NAME}",
            ).build()
        }

        @Provides
        @Singleton
        fun provideLocalizationDao(database: AppCoreDatabase): LocalizationDao {
            return database.localizationDao()
        }
    }
}