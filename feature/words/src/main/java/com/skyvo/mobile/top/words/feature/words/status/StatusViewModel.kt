package com.skyvo.mobile.top.words.feature.words.status

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
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
                    if (course.progress < 0.25f) {
                        setState {
                            copy(
                                isWordCardCompleted = false,
                                isBlankFillQuizCompleted = false,
                                isWordQuizCompleted = false,
                                isSentenceQuizCompleted = false
                            )
                        }
                    } else  if (course.progress < 0.50f) {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isBlankFillQuizCompleted = false,
                                isWordQuizCompleted = false,
                                isSentenceQuizCompleted = false
                            )
                        }
                    } else  if (course.progress < 0.75f) {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isBlankFillQuizCompleted = true,
                                isWordQuizCompleted = false,
                                isSentenceQuizCompleted = false
                            )
                        }
                    } else  if (course.progress > 0.75f && course.progress < 1f) {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isBlankFillQuizCompleted = true,
                                isWordQuizCompleted = true,
                                isSentenceQuizCompleted = false
                            )
                        }
                    } else {
                        setState {
                            copy(
                                isWordCardCompleted = true,
                                isBlankFillQuizCompleted = true,
                                isWordQuizCompleted = true,
                                isSentenceQuizCompleted = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun next() {
        val progress = state.value.currentCourse?.progress ?: 0f
        if (progress < 0.25f) {
            navigate(StatusFragmentDirections.actionStatusFragmentToFlashCardFragment())
        } else if (progress < 0.50f) {
            navigate(StatusFragmentDirections.actionStatusFragmentToSentenceQuizFragment())
        } else if (progress < 0.75f) {
            navigate(StatusFragmentDirections.actionStatusFragmentToFindMeaningQuizFragment())
        } else if (progress < 1f){
            navigate(StatusFragmentDirections.actionStatusFragmentToPuzzleQuizFragment())
        } else {
            viewModelScope.launch {
                courseWordRepository.updateCourse(
                    isStart = true,
                    progress = 1f
                )
            }
        }
    }
}