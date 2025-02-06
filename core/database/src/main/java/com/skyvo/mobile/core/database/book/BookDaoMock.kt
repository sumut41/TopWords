package com.skyvo.mobile.core.database.book

class BookDaoMock: BookDao {
    override suspend fun insertAll(words: List<BookEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun getBookList(level: String?, languageCode: String): List<BookEntity>? {
        TODO("Not yet implemented")
    }

    override suspend fun getBookDetail(id: Long): BookEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun searchBook(searchText: String?): List<BookEntity>? {
        TODO("Not yet implemented")
    }

    override suspend fun clearAll() {
        TODO("Not yet implemented")
    }
}