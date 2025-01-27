package com.skyvo.mobile.core.base.manager

interface FiDataManager {
    suspend fun fetch(onComplete: (Boolean) -> Unit)
    suspend fun getBeginnerWordList(): List<AppWord>?
    suspend fun getIntermediateWordList(): List<AppWord>?
    suspend fun getAdvancedWordList(): List<AppWord>?
    suspend fun getBeginnerBookList(): List<AppBook>?
    suspend fun getIntermediateBookList(): List<AppBook>?
    suspend fun getAdvancedBookList(): List<AppBook>?
}