package com.skyvo.mobile.top.words.feature.words.status

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.top.words.feature.words.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val courseWordRepository: CourseWordRepository
) : BaseComposeViewModel<StatusUIState>() {

    override fun setInitialState(): StatusUIState {
        return StatusUIState()
    }

    init {
        getCurrentCourse()
    }

    private fun getCurrentCourse() {
        viewModelScope.launch {
            courseWordRepository.getCurrentCourse().collect {
                it?.let { course ->
                    setState {
                        copy(
                            currentCourse = course
                        )
                    }
                    delay(100)
                    if (course.progress < 0.20f) {
                        setState {
                            copy(
                                isWordCardCompleted = false,
                                isBlankFillQuizCompleted = false,
                                isTranslateQuizCompleted = false,
                                isWriteQuizCompleted = false,
                                isReverseQuizCompleted = false
                            )
                        }
                    } else  if (course.progress < 0.40f) {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isReverseQuizCompleted = false,
                                isBlankFillQuizCompleted = false,
                                isTranslateQuizCompleted = false,
                                isWriteQuizCompleted = false
                            )
                        }
                    } else  if (course.progress < 0.60f) {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isTranslateQuizCompleted = true,
                                isBlankFillQuizCompleted = false,
                                isWriteQuizCompleted = false,
                                isReverseQuizCompleted = false
                            )
                        }
                    } else  if (course.progress < 0.80f) {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isTranslateQuizCompleted = true,
                                isBlankFillQuizCompleted = false,
                                isWriteQuizCompleted = false,
                                isReverseQuizCompleted = true
                            )
                        }
                    } else  if (course.progress > 0.80f && course.progress < 1f) {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isBlankFillQuizCompleted = false,
                                isTranslateQuizCompleted = true,
                                isReverseQuizCompleted = true,
                                isWriteQuizCompleted = false
                            )
                        }
                    } else {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isBlankFillQuizCompleted = true,
                                isTranslateQuizCompleted = true,
                                isWriteQuizCompleted = true,
                                isReverseQuizCompleted = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun next() {
        val progress = state.value.currentCourse?.progress ?: 0f
        if (progress < 0.20f) {
            navigate(StatusFragmentDirections.actionStatusFragmentToFlashCardFragment())
        } else if (progress < 0.40f) {
            navigate(StatusFragmentDirections.actionStatusFragmentToFindMeaningQuizFragment())
        } else if (progress < 0.60f) {
            navigate(StatusFragmentDirections.actionStatusFragmentToReverseMeaningQuizFragment())
        } else if (progress < 0.80f) {
            navigate(StatusFragmentDirections.actionStatusFragmentToSentenceQuizFragment())
        } else if (progress < 1f){
            navigate(StatusFragmentDirections.actionStatusFragmentToPuzzleQuizFragment())
        } else {
            navigate(
                navDeepLink = NavDeeplinkDestination.ResultWord(
                    "Oooo ${state.value.currentCourse?.id}. Ders Bitti!"
                ),
                popUpTo = true,
                popUpToInclusive = false,
                popUpToId = R.id.wordsDashboardFragment
            )
        }
    }
}