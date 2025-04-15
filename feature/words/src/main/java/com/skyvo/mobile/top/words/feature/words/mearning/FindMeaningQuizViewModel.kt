package com.skyvo.mobile.top.words.feature.words.mearning

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.AppWordTranslateItem
import com.skyvo.mobile.core.base.manager.LevelType
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.core.shared.extension.convertJsonToList
import com.skyvo.mobile.top.words.feature.words.R
import com.skyvo.mobile.top.words.feature.words.sentence.SentenceQuizModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindMeaningQuizViewModel @Inject constructor(
    private val courseWordRepository: CourseWordRepository,
    private val wordRepository: WordRepository,
    private val userManager: UserManager
) : BaseComposeViewModel<FindMeaningQuizUIState>() {

    private var currentProgress: Float = 0.0f
    private var isSingleJustQuiz: Boolean = false

    override fun setInitialState(): FindMeaningQuizUIState {
        return FindMeaningQuizUIState()
    }

    override fun fetchExtras(extra: Bundle) {
        super.fetchExtras(extra)
        with(FindMeaningQuizFragmentArgs.fromBundle(extra)) {
            isSingleJustQuiz = isSingleQuiz
            if (isSingleQuiz) {
                getRandomLearnWord()
            } else {
                getCurrentCourse()
            }
        }
    }

    private fun getRandomLearnWord() {
        setState {
            copy(
                isSingleQuiz = true
            )
        }
        val questionList: ArrayList<SentenceQuizModel> = arrayListOf()
        viewModelScope.launch {
            showLoading()
            wordRepository.getRandomLevelWordList(
                level = getLevel().orEmpty(),
                languageCode = userManager.learnLanguage?.code.orEmpty()
            ).collect {
                it?.forEach { word ->
                    questionList.add(
                        SentenceQuizModel(
                            word = word.word.orEmpty(),
                            question = word.translate.orEmpty(),
                            questionTranslate = "Anlamına uygun seçeneği seç.",
                            answerList = word.translateList?.convertJsonToList<AppWordTranslateItem>()
                                ?.shuffled()?.shuffled()
                        )
                    )
                }

                questionList.shuffled().shuffled()
                setState {
                    copy(
                        wordIdListSize = questionList.size,
                        items = questionList,
                        currentQuestion = questionList.first()
                    )
                }
            }

            removeAllLoading()
        }
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
                    wordRepository.getStudyWord(id.toLong()).collect {
                        it?.let { word ->
                            questionList.add(
                                SentenceQuizModel(
                                    word = word.word.orEmpty(),
                                    question = word.translate.orEmpty(),
                                    answerList = word.translateList?.convertJsonToList<AppWordTranslateItem>()
                                        ?.shuffled()?.shuffled()
                                )
                            )
                        }
                    }
                }
                questionList.shuffled().shuffled()
                setState {
                    copy(
                        wordIdListSize = wordIdList.size,
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
                correctCount = correctCount + 1,
                playSoundType = 0
            )
        }
    }

    private fun unCorrect() {
        setState {
            copy(
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
                    navDeepLink = NavDeeplinkDestination.ResultWord(isQuiz = true),
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
                            ?: 1) - 1)
                    ) 0.40f else (if (currentProgress == 0.20f) 0.30f else currentProgress)
                )
                delay(150)
                if (isBack) {
                    navigateBack()
                } else {
                    navigate(
                        navDeepLink = NavDeeplinkDestination.ResultWord(),
                        popUpTo = true,
                        popUpToInclusive = false,
                        popUpToId = R.id.wordsDashboardFragment
                    )
                }
            }
        }
    }

    private fun getLevel(): String? {
        return when (userManager.customerLevel?.type) {
            "A1", "A2" -> LevelType.BEGINNER.key
            "B1", "B2" -> LevelType.INTERMEDIATE.key
            "C1", "C2" -> LevelType.ADVANCED.key
            else -> LevelType.BEGINNER.key
        }
    }
}