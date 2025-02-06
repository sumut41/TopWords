package com.skyvo.mobile.core.database.book

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class BookRepository @Inject constructor(
    private val bookDao: BookDao
) {
    suspend fun insertAll(list: List<BookEntity>) {
        bookDao.insertAll(list)
    }

    fun getBookList(level: String?, languageCode: String): Flow<List<BookEntity>?> = flow {
        emit(bookDao.getBookList(level, languageCode))
    }.flowOn(Dispatchers.IO)

    fun getBookDetail(bookId: Long): Flow<BookEntity?> = flow {
        emit(bookDao.getBookDetail(bookId))
    }.flowOn(Dispatchers.IO)

    fun search(searchQuery: String?): Flow<List<BookEntity>?> = flow {
        emit(bookDao.searchBook(searchQuery))
    }.flowOn(Dispatchers.IO)

    suspend fun clearAll() {
        bookDao.clearAll()
    }
}