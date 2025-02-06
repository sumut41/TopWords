package com.skyvo.mobile.core.database.word

class WorkDaoMock: WordDao {
    override suspend fun insertAll(words: List<WordEntity>) {
        // insert
    }

    override suspend fun getLevelWordList(level: String, languageCode: String): List<WordEntity>? {
        return emptyList()
    }

    override suspend fun getSearchWordTranslate(
        word: String,
        languageCode: String
    ): List<WordEntity>? {
        return emptyList()
    }

    override suspend fun getFavoriteWordList(languageCode: String): List<WordEntity>? {
        return emptyList()
    }

    override suspend fun getLearnedWordList(languageCode: String): List<WordEntity>? {
        return emptyList()
    }

    override suspend fun getNotMuchWordList(languageCode: String): List<WordEntity>? {
        return emptyList()
    }

    override suspend fun getStudyWordList(level: String, languageCode: String): List<WordEntity>? {
        return emptyList()
    }

    override suspend fun markWordAsKnown(id: Long, languageCode: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun markWordAsUnKnown(id: Long, languageCode: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun markWordAsFavorite(isFavorite: Boolean, id: Long, languageCode: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getWord(id: Long): WordEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun updateWord(word: WordEntity) {
        // update
    }

    override suspend fun clearAll() {
        TODO("Not yet implemented")
    }
}