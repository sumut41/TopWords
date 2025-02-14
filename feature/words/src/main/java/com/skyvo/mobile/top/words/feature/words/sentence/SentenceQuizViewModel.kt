package com.skyvo.mobile.top.words.feature.words.sentence

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.AppWordTranslateItem
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.core.shared.extension.convertJsonToList
import com.skyvo.mobile.top.words.feature.words.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
        setState {
            copy(
                learnLanguageCode = userManager.learnLanguage?.code
            )
        }
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
            val wordIdList = (wordIds?.split(","))
            wordIdList?.let { list ->
                list.shuffled().forEach { id ->
                    wordRepository.getQuizWord(id.toLong()).collect {
                        it?.let { word ->
                            questionList.add(
                                SentenceQuizModel(
                                    word = word.word.orEmpty(),
                                    question = word.quiz.orEmpty(),
                                    questionTranslate = word.quizTranslate.orEmpty(),
                                    answerList = word.translateList?.convertJsonToList<AppWordTranslateItem>()
                                        ?.shuffled(),
                                )
                            )
                        }
                    }
                }
                questionList.shuffled()
                setState {
                    copy(
                        wordIdListSize = wordIdList.size,
                        items = questionList,
                        unCorrectCount = wordIdList.size - questionList.size,
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
                if (index >= (state.value.items?.size ?: 0)) {
                    next()
                } else {
                    setState {
                        copy(
                            showAnswer = false,
                            selectAnswer = null,
                            currentQuestion = items?.get(index)
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

    fun next(isBack: Boolean = false) {
        viewModelScope.launch {
            courseWordRepository.updateCourse(
                id = state.value.courseId ?: 0L,
                isStart = true,
                progress = if (state.value.correctCount >= ((state.value.items?.size ?: 1) - 1) && state.value.unCorrectCount == 0) 0.50f else (if (currentProgress == 0.25f) 0.30f else currentProgress)
            )
            delay(100)
            if (isBack) {
                navigateBack()
            } else {
                navigate(
                    navDeepLink = NavDeeplinkDestination.ResultWord(
                        "Süpersin!"
                    ),
                    popUpTo = true,
                    popUpToInclusive = false,
                    popUpToId = R.id.wordsDashboardFragment
                )
            }
        }
    }
}