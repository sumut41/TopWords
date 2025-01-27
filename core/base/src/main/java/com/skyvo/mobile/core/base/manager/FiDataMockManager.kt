package com.skyvo.mobile.core.base.manager

class FiDataMockManager: FiDataManager {
    override suspend fun fetch(onComplete: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun getBeginnerWordList(): List<AppWord>? {
        TODO("Not yet implemented")
    }

    override suspend fun getIntermediateWordList(): List<AppWord>? {
        TODO("Not yet implemented")
    }

    override suspend fun getAdvancedWordList(): List<AppWord>? {
        TODO("Not yet implemented")
    }

    override suspend fun getBeginnerBookList(): List<AppBook>? {
        TODO("Not yet implemented")
    }

    override suspend fun getIntermediateBookList(): List<AppBook>? {
        TODO("Not yet implemented")
    }

    override suspend fun getAdvancedBookList(): List<AppBook>? {
        TODO("Not yet implemented")
    }
}