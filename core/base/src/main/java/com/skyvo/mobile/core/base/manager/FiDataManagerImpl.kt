package com.skyvo.mobile.core.base.manager

import com.skyvo.mobile.core.base.firebase.RemoteConfigManager
import com.skyvo.mobile.core.shared.extension.toModel
import javax.inject.Inject

class FiDataManagerImpl @Inject constructor(
    private val remoteConfigManager: RemoteConfigManager,
    private val userManager: UserManager
): FiDataManager {

    private var beginnerBookList: List<AppBook>? = listOf()
    private var intermediateBookList: List<AppBook>? = listOf()
    private var advancedBookList: List<AppBook>? = listOf()

    private var beginnerWordList: List<AppWord>? = listOf()
    private var intermediateWordList: List<AppWord>? = listOf()
    private var advancedWordList: List<AppWord>? = listOf()

    override suspend fun fetch(onComplete: (Boolean) -> Unit) {
        try {
            val nativeLanguageCode = userManager.nativeLanguage?.code
            val learnLanguageCode = userManager.learnLanguage?.code

            beginnerBookList = remoteConfigManager.getString(
                "books_beginner_${nativeLanguageCode}_$learnLanguageCode"
            ).toModel<AppBookParentModel>()?.books

            intermediateBookList = remoteConfigManager.getString(
                "books_intermediate_${nativeLanguageCode}_$learnLanguageCode"
            ).toModel<AppBookParentModel>()?.books

            advancedBookList = remoteConfigManager.getString(
                "books_advanced_${nativeLanguageCode}_$learnLanguageCode"
            ).toModel<AppBookParentModel>()?.books

            beginnerWordList = remoteConfigManager.getString(
                "words_beginner_${nativeLanguageCode}_$learnLanguageCode"
            ).toModel<AppWordParentModel>()?.wordList

            intermediateWordList = remoteConfigManager.getString(
                "words_intermediate_${nativeLanguageCode}_$learnLanguageCode"
            ).toModel<AppWordParentModel>()?.wordList

            advancedWordList = remoteConfigManager.getString(
                "words_advanced_${nativeLanguageCode}_$learnLanguageCode"
            ).toModel<AppWordParentModel>()?.wordList

            onComplete.invoke(true)
        } catch (ex: Exception) {
            onComplete.invoke(false)
        }
    }

    override suspend fun getBeginnerWordList(): List<AppWord>? {
        return beginnerWordList
    }

    override suspend fun getIntermediateWordList(): List<AppWord>? {
        return intermediateWordList
    }

    override suspend fun getAdvancedWordList(): List<AppWord>? {
        return advancedWordList
    }

    override suspend fun getBeginnerBookList(): List<AppBook>? {
        return beginnerBookList
    }

    override suspend fun getIntermediateBookList(): List<AppBook>? {
        return intermediateBookList
    }

    override suspend fun getAdvancedBookList(): List<AppBook>? {
        return advancedBookList
    }


}