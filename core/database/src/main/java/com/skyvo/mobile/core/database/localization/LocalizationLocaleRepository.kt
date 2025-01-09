package com.skyvo.mobile.core.database.localization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LocalizationLocaleRepository @Inject constructor(private val localizationDao: LocalizationDao) {
    suspend fun insertStringsToDatabase(stringList: List<LocalizationEntity>) {
        runBlocking {
            launch(Dispatchers.IO) {
                localizationDao.insertAll(stringList)
            }
        }
    }

    suspend fun getAllStringResource(): List<LocalizationEntity> {
        return localizationDao.getAllStringResource()
    }
}