package com.skyvo.mobile.top.words.feature.words.puzzle

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.top.words.feature.words.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PuzzleQuizViewModel @Inject constructor(
    private val courseWordRepository: CourseWordRepository,
    private val wordRepository: WordRepository
) : BaseComposeViewModel<PuzzleQuizUIState>() {

    private var currentProgress: Float = 0.0f

    override fun setInitialState(): PuzzleQuizUIState {
        return PuzzleQuizUIState()
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
            val questionList: ArrayList<PuzzleModel> = arrayListOf()
            val wordIdList = (wordIds?.split(","))
            wordIdList?.let { list ->
                list.shuffled().forEach { id ->
                    wordRepository.getStudyWord(id.toLong()).collect {
                        it?.let { word ->
                            questionList.add(
                                PuzzleModel(
                                    word = word.word.orEmpty(),
                                    translate = word.translate.orEmpty()
                                )
                            )
                        }
                    }
                }
                questionList.shuffled().shuffled()
                setState {
                    copy(
                        selectIndex = 0,
                        wordList = questionList,
                        buttonEnable = false,
                        currentQuestion = questionList.first()
                    )
                }
            }
            removeAllLoading()
        }
    }

    private fun correct() {
        setState {
            copy(
                correctCount = correctCount + 1,
                playSoundType = 0
            )
        }
    }

    private fun unCorrect() {
        setState {
            copy(
                unCorrectCount = unCorrectCount + 1,
                playSoundType = 1
            )
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            delay(120)
            if (state.value.nextCount == 1) {
                setState {
                    copy(
                        showAnswer = false,
                        nextCount = 0,
                        selectIndex = selectIndex + 1
                    )
                }
                delay(250)
                val index = state.value.selectIndex
                if (index >= (state.value.wordList?.size ?: 0)) {
                    next()
                } else {
                    setState {
                        copy(
                            showAnswer = false,
                            selectAnswer = null,
                            currentQuestion = wordList?.get(index)
                        )
                    }
                }
            } else {
                setState {
                    copy(
                        nextCount = 1
                    )
                }
                delay(150)
                setState {
                    copy(
                        showAnswer = true
                    )
                }
            }
        }
    }

    fun checkAnswer() {
        val answerList = state.value.currentQuestion?.word?.split(",")
        if (answerList != null) {
            for (item in answerList) {
                if (item.lowercase().trim() == state.value.selectAnswer?.lowercase()?.trim()) {
                    correct()
                } else {
                    unCorrect()
                }
            }
        } else {
            unCorrect()
        }

        nextQuestion()
    }

    fun updateAnswer(answer: String) {
        setState {
            copy(
                selectAnswer = answer,
                buttonEnable = answer.isNotEmpty()
            )
        }
    }

    fun next(isBack: Boolean = false) {
        viewModelScope.launch {
            courseWordRepository.updateCourse(
                id = state.value.courseId ?: 0L,
                isStart = true,
                progress = if (state.value.correctCount >= ((state.value.wordList?.size ?: 1) - 1)) 1f else (if (currentProgress == 0.80f) 0.90f else currentProgress)
            )
            delay(100)
            if (isBack) {
                navigateBack()
            } else {
                navigate(
                    navDeepLink = NavDeeplinkDestination.ResultWord(
                        "Oooo ${state.value.courseId}. Ders Bitti!"
                    ),
                    popUpTo = true,
                    popUpToInclusive = false,
                    popUpToId = R.id.wordsDashboardFragment
                )
            }
        }
    }
}