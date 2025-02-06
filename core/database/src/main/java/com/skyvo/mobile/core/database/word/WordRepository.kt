package com.skyvo.mobile.core.database.word

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class WordRepository @Inject constructor(
    private val wordDao: WordDao
) {
    suspend fun insertWordList(wordList: List<WordEntity>) {
        wordDao.insertAll(wordList)
    }

    fun getStudyWordList(level: String?, languageCode: String?): Flow<List<WordEntity>?> = flow {
        emit(wordDao.getStudyWordList(level.orEmpty(), languageCode.orEmpty()))
    }.flowOn(Dispatchers.IO)

    fun getLevelWordList(level: String, languageCode: String): Flow<List<WordEntity>?> = flow {
        emit(wordDao.getLevelWordList(level, languageCode))
    }.flowOn(Dispatchers.IO)

    fun getFavoriteWordList(languageCode: String): Flow<List<WordEntity>?> = flow {
        emit(wordDao.getFavoriteWordList(languageCode))
    }.flowOn(Dispatchers.IO)

    fun getLearnedWordList(languageCode: String): Flow<List<WordEntity>?> = flow {
        emit(wordDao.getLearnedWordList(languageCode))
    }.flowOn(Dispatchers.IO)

    fun getNotMuchWordList(languageCode: String): Flow<List<WordEntity>?> = flow {
        emit(wordDao.getNotMuchWordList(languageCode))
    }.flowOn(Dispatchers.IO)

    fun searchWord(word: String, languageCode: String): Flow<List<WordEntity>?> = flow {
        emit(wordDao.getSearchWordTranslate(word, languageCode))
    }.flowOn(Dispatchers.IO)

    suspend fun updateWord(word: WordEntity) {
        wordDao.updateWord(word)
    }

    suspend fun markWordAsKnown(id: Long, languageCode: String?) {
        wordDao.markWordAsKnown(id, languageCode)
    }

    suspend fun markWordAsUnKnown(id: Long, languageCode: String?) {
        wordDao.markWordAsUnKnown(id, languageCode)
    }

    suspend fun markWordAsFavorite(isFavorite: Boolean, id: Long, languageCode: String?) {
        wordDao.markWordAsFavorite(isFavorite, id, languageCode)
    }

    fun getWord(id: Long): Flow<WordEntity?> = flow {
        emit(wordDao.getWord(id))
    }.flowOn(Dispatchers.IO)

    fun getStudyWord(id: Long): Flow<WordEntity?> = flow {
        emit(wordDao.getStudyWord(id))
    }.flowOn(Dispatchers.IO)

    suspend fun clearAll() {
        wordDao.clearAll()
    }
}