package com.skyvo.mobile.top.words.loader

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.firebase.RemoteConfigManager
import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.manager.AppBookParentModel
import com.skyvo.mobile.core.base.manager.AppWord
import com.skyvo.mobile.core.base.manager.AppWordParentModel
import com.skyvo.mobile.core.base.manager.LevelType
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.book.BookEntity
import com.skyvo.mobile.core.database.book.BookRepository
import com.skyvo.mobile.core.database.course.CourseWordEntity
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordEntity
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.core.shared.extension.toJson
import com.skyvo.mobile.core.shared.extension.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataLoaderViewModel @Inject constructor(
    private val userManager: UserManager,
    private val remoteConfigManager: RemoteConfigManager,
    private val wordRepository: WordRepository,
    private val courseWordRepository: CourseWordRepository,
    private val bookRepository: BookRepository
) : BaseComposeViewModel<DataLoaderUIState>() {

    private var level: Int = 0

    private var beginnerBookList: List<AppBook>? = listOf()
    private var intermediateBookList: List<AppBook>? = listOf()
    private var advancedBookList: List<AppBook>? = listOf()

    private var beginnerWordList: List<AppWord>? = listOf()
    private var intermediateWordList: List<AppWord>? = listOf()
    private var advancedWordList: List<AppWord>? = listOf()

    override fun setInitialState(): DataLoaderUIState {
        return DataLoaderUIState()
    }

    init {
        setState {
            copy(
                language = userManager.learnLanguage?.name
            )
        }
        viewModelScope.launch {
            remoteConfigManager.initRemoteConfig {
                fetchRemoteConfigData { isComplete ->
                    if (isComplete) {
                        setLocaleData()
                    } else {
                        showGenericError()
                    }
                }
            }
        }
    }

    private fun fetchRemoteConfigData(onComplete: (Boolean) -> Unit) {
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

    private fun setLocaleData() {
        viewModelScope.launch {
            val wordList: ArrayList<WordEntity> = arrayListOf()
            //beginner
            beginnerWordList?.forEach { item ->
                wordList.add(
                    WordEntity(
                        word = item.word,
                        translate = item.translate,
                        quiz = item.quiz,
                        quizTranslate = item.quizTranslate,
                        level = LevelType.BEGINNER.key,
                        languageCode = userManager.learnLanguage?.code,
                        translateList = item.translateList?.toJson()
                    )
                )
            }
            //intermediate
            intermediateWordList?.forEach { item ->
                wordList.add(
                    WordEntity(
                        word = item.word,
                        translate = item.translate,
                        quiz = item.quiz,
                        quizTranslate = item.quizTranslate,
                        level = LevelType.INTERMEDIATE.key,
                        languageCode = userManager.learnLanguage?.code,
                        translateList = item.translateList?.toJson()
                    )
                )
            }
            //advanced
            advancedWordList?.forEach { item ->
                wordList.add(
                    WordEntity(
                        word = item.word,
                        translate = item.translate,
                        quiz = item.quiz,
                        quizTranslate = item.quizTranslate,
                        level = LevelType.ADVANCED.key,
                        languageCode = userManager.learnLanguage?.code,
                        translateList = item.translateList?.toJson()
                    )
                )
            }
            wordRepository.insertWordList(wordList)
            delay(600)
            loadWords()
        }
    }

    private fun loadWords() {
        when (userManager.customerLevel?.type) {
            "A1", "A2" -> getBeginnerWordList()
            "B1", "B2" -> getIntermediateWordList()
            "C1", "C2" -> getAdvancedWordList()
            else -> getBeginnerWordList()
        }
    }

    private fun getBeginnerWordList() {
        viewModelScope.launch {
            level = 1
            wordRepository.getStudyWordList(
                level = LevelType.BEGINNER.key,
                languageCode = userManager.learnLanguage?.code
            ).collect { response ->
                shuffleWord(response, LevelType.BEGINNER)
            }
        }
    }

    private fun getIntermediateWordList() {
        viewModelScope.launch {
            level = 2
            wordRepository.getStudyWordList(
                level = LevelType.INTERMEDIATE.key,
                languageCode = userManager.learnLanguage?.code
            ).collect { response ->
                shuffleWord(response, LevelType.INTERMEDIATE)
            }
        }
    }

    private fun getAdvancedWordList() {
        viewModelScope.launch {
            level = 3
            wordRepository.getStudyWordList(
                level = LevelType.ADVANCED.key,
                languageCode = userManager.learnLanguage?.code
            ).collect { response ->
                shuffleWord(response, LevelType.ADVANCED)
            }
        }
    }

    private fun shuffleWord(wordList: List<WordEntity>?, levelType: LevelType) {
        val courseList = wordList?.let { words ->
            val shuffledWords = (1..3).fold(words) { acc, _ -> acc.shuffled() }
            shuffledWords.chunked(userManager.goalMinute ?: 10)
        } ?: emptyList()

        val list: ArrayList<CourseWordEntity> = arrayListOf()
        courseList.forEach { course ->
            list.add(
                CourseWordEntity(
                    level = levelType.key,
                    wordIds = getIdList(course).joinToString(",")
                )
            )
        }
        viewModelScope.launch {
            courseWordRepository.insertAll(list)
            delay(600)
            loadMoreCourseWords()
        }
    }

    private fun loadMoreCourseWords() {
        when (level) {
            1 -> {
                getIntermediateWordList()
            }

            2 -> {
                getAdvancedWordList()
            }

            else -> {
                saveBook()
            }
        }
    }

    private fun saveBook() {
        viewModelScope.launch {
            bookRepository.insertAll(beginnerBook().orEmpty())
            delay(350)
            bookRepository.insertAll(intermediateBook().orEmpty())
            delay(350)
            bookRepository.insertAll(advancedBook().orEmpty())
            userManager.isCompletedSetup = true
            delay(500)
            navigate(NavDeeplinkDestination.WordsDashboard)
        }
    }

    private fun beginnerBook(): List<BookEntity>? {
        return beginnerBookList?.map { book ->
            BookEntity(
                categoryLevel = LevelType.BEGINNER.key,
                level = book.level,
                languageCode = userManager.learnLanguage?.code,
                content = book.content,
                contentTr = book.contentTr,
                title = book.title,
                min = book.min,
                genre = book.genre,
                isNew = book.isNew,
                imageUrl = book.imageUrl,
                words = book.words.toJson()
            )
        }
    }

    private fun intermediateBook(): List<BookEntity>? {
        return intermediateBookList?.map { book ->
            BookEntity(
                categoryLevel = LevelType.INTERMEDIATE.key,
                level = book.level,
                languageCode = userManager.learnLanguage?.code,
                content = book.content,
                contentTr = book.contentTr,
                title = book.title,
                min = book.min,
                genre = book.genre,
                isNew = book.isNew,
                imageUrl = book.imageUrl,
                words = book.words.toJson()
            )
        }
    }

    private fun advancedBook(): List<BookEntity>? {
        return advancedBookList?.map { book ->
            BookEntity(
                categoryLevel = LevelType.ADVANCED.key,
                level = book.level,
                languageCode = userManager.learnLanguage?.code,
                content = book.content,
                contentTr = book.contentTr,
                title = book.title,
                min = book.min,
                genre = book.genre,
                isNew = book.isNew,
                imageUrl = book.imageUrl,
                words = book.words.toJson()
            )
        }
    }

    private fun getIdList(wordList: List<WordEntity>?): ArrayList<Long> {
        val idList: ArrayList<Long> = arrayListOf()
        wordList?.forEach {
            idList.add(it.id)
        }
        return idList
    }
}