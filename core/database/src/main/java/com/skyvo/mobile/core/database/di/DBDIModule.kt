package com.skyvo.mobile.core.database.di

import android.content.Context
import androidx.room.Room
import com.skyvo.mobile.core.database.AppCoreDatabase
import com.skyvo.mobile.core.database.RoomDatabaseConstant
import com.skyvo.mobile.core.database.localization.LocalizationDao
import com.skyvo.mobile.core.database.word.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBDIModule {
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

    @Provides
    @Singleton
    fun provideWordDao(database: AppCoreDatabase): WordDao {
        return database.wordDao()
    }
}