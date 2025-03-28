package com.skyvo.mobile.top.words.feature.words.mearning

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.AppWordTranslateItem
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordEntity
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.top.words.feature.words.R
import com.skyvo.mobile.top.words.feature.words.sentence.SentenceQuizModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReverseMeaningQuizViewModel @Inject constructor(
    private val courseWordRepository: CourseWordRepository,
    private val wordRepository: WordRepository
) : BaseComposeViewModel<ReverseMeaningQuizUIState>() {

    private var currentProgress: Float = 0.0f
    private var isSingleJustQuiz: Boolean = false

    override fun setInitialState(): ReverseMeaningQuizUIState {
        return ReverseMeaningQuizUIState()
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
            val wordList: ArrayList<WordEntity> = arrayListOf()
            val wordIdList = (wordIds?.split(","))
            wordIdList?.forEach { id ->
                wordRepository.getStudyWord(id.toLong()).collect {
                    it?.let { word ->
                        wordList.add(word)
                    }
                }
            }

            wordList.forEach { word ->
                questionList.add(
                    SentenceQuizModel(
                        word = word.translate.orEmpty(),
                        question = word.word.orEmpty(),
                        answerList = getAnswerList(wordList, word.translate.orEmpty()).shuffled().shuffled()
                    )
                )
            }

            questionList.shuffled().shuffled()
            setState {
                copy(
                    wordIdListSize = wordIdList.orEmpty().size,
                    items = questionList,
                    unCorrectCount = wordIdList.orEmpty().size - questionList.size,
                    currentQuestion = questionList.first()
                )
            }
        }
        removeAllLoading()
    }

    private fun getAnswerList(wordList: ArrayList<WordEntity>, word: String): List<AppWordTranslateItem> {
        val list: ArrayList<AppWordTranslateItem> = arrayListOf()

        wordList.filter { x -> x.translate != word }.shuffled().take(3).forEach {
            list.add(
                AppWordTranslateItem(
                    label = it.translate,
                    isCorrect = false
                )
            )
        }
        list.add(
            AppWordTranslateItem(
                label = word,
                isCorrect = true
            )
        )
        return list
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
                        questionIndex = questionIndex + 1,
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
        if (isSingleJustQuiz) {
            if (isBack) {
                navigateBack()
            } else {
                navigate(
                    navDeepLink = NavDeeplinkDestination.ResultWord(
                        title = "Süpersin, devam!",
                        isQuiz = true
                    ),
                    popUpTo = true,
                    popUpToInclusive = false,
                    popUpToId = R.id.wordsDashboardFragment
                )
            }
        } else {
            viewModelScope.launch {
                courseWordRepository.updateCourse(
                    id = state.value.courseId ?: 0L,
                    isStart = true,
                    progress = if (state.value.correctCount >= ((state.value.items?.size
                            ?: 1) - 1) && state.value.unCorrectCount == 0
                    ) 0.60f else (if (currentProgress == 0.40f) 0.50f else currentProgress)
                )
                delay(100)
                if (isBack) {
                    navigateBack()
                } else {
                    navigate(
                        navDeepLink = NavDeeplinkDestination.ResultWord(
                            "Harika gidiyorsun!"
                        ),
                        popUpTo = true,
                        popUpToInclusive = false,
                        popUpToId = R.id.wordsDashboardFragment
                    )
                }
            }
        }
    }
}