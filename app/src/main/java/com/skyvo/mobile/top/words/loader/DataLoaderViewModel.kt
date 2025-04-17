package com.skyvo.mobile.top.words.loader

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.firebase.RemoteConfigManager
import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.manager.AppBookParentModel
import com.skyvo.mobile.core.base.manager.AppWord
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
import com.skyvo.mobile.core.shared.exception.ExceptionFBHelper.Companion.recordException
import com.skyvo.mobile.core.shared.extension.toJson
import com.skyvo.mobile.core.shared.extension.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var beginnerWordList: List<AppWord>? = listOf()
    private var intermediateWordList: List<AppWord>? = listOf()
    private var advancedWordList: List<AppWord>? = listOf()

    override fun setInitialState(): DataLoaderUIState {
        return DataLoaderUIState()
    }

    init {
        setState {
            copy(
                language = userManager.learnLanguage?.name,
                nativeLanguageCode = userManager.nativeLanguage?.code,
                learnLanguageCode = userManager.learnLanguage?.code
            )
        }
    }

    fun setBeginnerWord(wordList: List<AppWord>?) {
        beginnerWordList = wordList
    }

    fun setIntermediate(wordList: List<AppWord>?) {
        intermediateWordList = wordList
    }

    fun setAdvanced(wordList: List<AppWord>?) {
        advancedWordList = wordList
    }

    fun getBookData() {
        viewModelScope.launch {
            remoteConfigManager.initRemoteConfig {
                fetchRemoteConfigData {
                    setLocaleData()
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

            onComplete.invoke(true)
        } catch (ex: Exception) {
            onComplete.invoke(false)
        }
    }

    private fun setLocaleData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val wordList = ArrayList<WordEntity>()

                // Tüm kelimeleri tek seferde dönüştür
                beginnerWordList?.forEach { item ->
                    wordList.add(createWordEntity(item, LevelType.BEGINNER))
                }
                intermediateWordList?.forEach { item ->
                    wordList.add(createWordEntity(item, LevelType.INTERMEDIATE))
                }
                advancedWordList?.forEach { item ->
                    wordList.add(createWordEntity(item, LevelType.ADVANCED))
                }

                // Veritabanı işlemlerini IO thread'de yap
                wordRepository.insertWordList(wordList)
                loadWords()
            } catch (e: Exception) {
                recordException(e)
            }
        }
    }

    private fun createWordEntity(item: AppWord, level: LevelType) = WordEntity(
        word = item.word,
        translate = item.translate,
        quiz = item.quiz,
        quizTranslate = item.quizTranslate,
        level = level.key,
        languageCode = userManager.learnLanguage?.code,
        translateList = item.translateList?.toJson()
    )

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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val courseList = wordList?.let { words ->
                    words.shuffled().chunked(userManager.goalMinute ?: 10)
                } ?: emptyList()

                val list = courseList.map { course ->
                    CourseWordEntity(
                        level = levelType.key,
                        wordIds = getIdList(course).joinToString(",")
                    )
                }

                courseWordRepository.insertAll(list)
                loadMoreCourseWords()
            } catch (e: Exception) {
                recordException(e)
            }
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allBooks = (beginnerBook().orEmpty() + intermediateBook().orEmpty())
                bookRepository.insertAll(allBooks)
                userManager.isCompletedSetup = true

                withContext(Dispatchers.Main) {
                    navigate(NavDeeplinkDestination.WordsDashboard)
                }
            } catch (e: Exception) {
                recordException(e)
            }
        }
    }

    private fun beginnerBook(): List<BookEntity>? {
        return beginnerBookList?.map { book ->
            BookEntity(
                categoryLevel = LevelType.BEGINNER.key,
                level = book.level,
                languageCode = userManager.learnLanguage?.code,
                content = book.content,
                contentTr = book.contentTranslate,
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
                contentTr = book.contentTranslate,
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