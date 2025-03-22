package com.skyvo.mobile.top.words.feature.words.flashcard

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.core.uikit.compose.card.FlashcardItem
import com.skyvo.mobile.top.words.feature.words.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    private val userManager: UserManager,
    private val courseWordRepository: CourseWordRepository,
    private val wordRepository: WordRepository
): BaseComposeViewModel<FlashCardUIState>() {

    private var currentProgress: Float = 0.0f

    override fun setInitialState(): FlashCardUIState {
        return FlashCardUIState()
    }

    init {
        setState {
            copy(
                learnLanguageCode = userManager.learnLanguage?.code
            )
        }
        getCurrentCourse()
    }

    private fun getCurrentCourse() {
        viewModelScope.launch {
            courseWordRepository.getCurrentCourse().collect {
                it?.let { course ->
                    setState {
                        copy(
                            courseId = course.id
                        )
                    }
                    currentProgress = course.progress
                    getWordList(wordIds = course.wordIds)
                }
            }
        }
    }

    private fun getWordList(wordIds: String?) {
        viewModelScope.launch {
            val wordList: ArrayList<FlashcardItem> = arrayListOf()
            wordIds?.split(",")?.forEach { id ->
                wordRepository.getStudyWord(id.toLong()).collect {
                    it?.let { word ->
                        wordList.add(
                            FlashcardItem(
                                id = word.id,
                                word = word.word.orEmpty(),
                                translate = word.translate.orEmpty(),
                                sentence = word.quiz.orEmpty().replace("....", word.word.orEmpty().lowercase()),
                                sentenceTranslate = word.quizTranslate.orEmpty(),
                                level = userManager.customerLevel?.name.orEmpty()
                            )
                        )
                    }
                }
            }
            setState {
                copy(
                    items = wordList
                )
            }
            if (wordList.isEmpty()) {
                navigate(
                    FlashCardFragmentDirections.actionFlashCardFragmentToSentenceQuizFragment()
                )
            }
        }
    }

    fun markWordAsKnown(word: FlashcardItem) {
        viewModelScope.launch {
            wordRepository.markWordAsKnown(word.id, userManager.learnLanguage?.code)
        }
    }

    fun markWordAsUnknown(word: FlashcardItem) {
        viewModelScope.launch {
            wordRepository.markWordAsUnKnown(word.id, userManager.learnLanguage?.code)
        }
    }

    fun toggleFavorite(itemId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            wordRepository.markWordAsFavorite(isFavorite, itemId, userManager.learnLanguage?.code)
        }
    }

    fun next() {
        viewModelScope.launch {
            delay(200)
            courseWordRepository.updateCourse(
                id = state.value.courseId ?: 0L,
                isStart = true,
                progress = 0.25f
            )
            navigate(
                navDeepLink = NavDeeplinkDestination.ResultWord(
                    "Kelimeler Bitti. Öğrenmeye devam edelim."
                ),
                popUpTo = true,
                popUpToInclusive = false,
                popUpToId = R.id.wordsDashboardFragment
            )
        }
    }
}