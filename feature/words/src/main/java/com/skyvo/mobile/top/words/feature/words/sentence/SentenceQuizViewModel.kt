package com.skyvo.mobile.top.words.feature.words.sentence

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.AppWordTranslateItem
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.core.shared.extension.convertJsonToList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SentenceQuizViewModel @Inject constructor(
    private val userManager: UserManager,
    private val courseWordRepository: CourseWordRepository,
    private val wordRepository: WordRepository
) : BaseComposeViewModel<SentenceQuizUIState>() {

    private var currentProgress: Float = 0.0f

    override fun setInitialState(): SentenceQuizUIState {
        return SentenceQuizUIState()
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
            val questionList: ArrayList<SentenceQuizModel> = arrayListOf()
            (wordIds?.split(","))?.let { list ->
                list.shuffled().forEach { id ->
                    wordRepository.getWord(id.toLong()).collect {
                        it?.let { word ->
                            questionList.add(
                                SentenceQuizModel(
                                    word = word.word.orEmpty(),
                                    question = word.quiz.orEmpty(),
                                    questionTranslate = word.quizTranslate.orEmpty(),
                                    answerList = word.translateList?.convertJsonToList<AppWordTranslateItem>(),
                                )
                            )
                        }
                    }
                }
                setState {
                    copy(
                        items = questionList,
                        currentQuestion = questionList.first()
                    )
                }
            }
            removeAllLoading()
        }
    }

    fun selectAnswer(answer: String, isCorrect: Boolean?) {
        setState {
            copy(
                selectAnswer = answer
            )
        }

        if (isCorrect == true) {
            correct()
        } else {
            unCorrect()
        }
    }

    private fun correct() {
        setState {
            copy(
                correctCount = correctCount + 1
            )
        }
    }

    private fun unCorrect() {
        setState {
            copy(
                unCorrectCount = unCorrectCount + 1
            )
        }
    }

    fun nextQuestion() {
        if (state.value.nextCount == 1) {
            setState {
                copy(
                    nextCount = 0,
                    showAnswer = false,
                    selectAnswer = null
                )
            }
            val index = state.value.selectIndex + 1
            if (index > (state.value.items?.size ?: 0)) {
                next()
            } else {
                setState {
                    copy(
                        currentQuestion = items?.get(index)
                    )
                }
            }
        } else {
            setState {
                copy(
                    showAnswer = true,
                    nextCount = 1
                )
            }
        }
    }

    fun next() {
        viewModelScope.launch {
            courseWordRepository.updateCourse(
                isStart = true,
                progress = calculateProgress(
                    state.value.items.orEmpty().size, state.value.correctCount
                )
            )
        }
    }

    private fun calculateProgress(totalWords: Int, knownWords: Int): Float {
        if (totalWords == 0) return currentProgress
        val progressIncrement = 0.25f * (knownWords.toFloat() / totalWords)
        currentProgress += progressIncrement
        return currentProgress
    }
}