package com.skyvo.mobile.top.words.feature.words.flashcard

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.core.uikit.compose.card.FlashcardItem
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
        getCurrentCourse()
    }

    private fun getCurrentCourse() {
        viewModelScope.launch {
            showLoading()
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
            removeAllLoading()
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
            setState {
                copy(
                    knowCount = knowCount + 1
                )
            }
        }
    }

    fun markWordAsUnknown(word: FlashcardItem) {
        viewModelScope.launch {
            wordRepository.markWordAsUnKnown(word.id, userManager.learnLanguage?.code)
            setState {
                copy(
                    unKnowCount = unKnowCount + 1
                )
            }
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
                isStart = true,
                progress = calculateProgress(state.value.items.orEmpty().size, state.value.knowCount)
            )
            navigate(FlashCardFragmentDirections.actionFlashCardFragmentToSentenceQuizFragment())
        }
    }

    private fun calculateProgress(totalWords: Int, knownWords: Int): Float {
        if (totalWords == 0) return currentProgress
        val progressIncrement = ((0.25f - currentProgress) * (knownWords.toFloat() / totalWords))
        currentProgress += progressIncrement
        return currentProgress
    }
}