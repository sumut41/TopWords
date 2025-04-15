package com.skyvo.mobile.top.words.feature.words.result

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordResultViewModel @Inject constructor(
    private val userManager: UserManager,
    private val courseWordRepository: CourseWordRepository
) : BaseComposeViewModel<WordResultUIState>() {

    var isQuizFrom: Boolean = false

    override fun setInitialState(): WordResultUIState {
        return WordResultUIState()
    }

    override fun fetchExtras(extra: Bundle) {
        super.fetchExtras(extra)
        with(WordResultFragmentArgs.fromBundle(extra)) {
            isQuizFrom = isQuiz
            getCurrentCourse()
        }
    }

    private fun getCurrentCourse() {
        viewModelScope.launch {
            courseWordRepository.getCurrentCourse().collect {
                it?.let { course ->
                    setState {
                        copy(
                            courseId = course.id,
                            progress = course.progress,
                            language = userManager.learnLanguage?.name
                        )
                    }
                }
            }
        }
    }

    fun next() {
        if (isQuizFrom) {
            navigate(NavDeeplinkDestination.WordsDashboard)
        } else {
            val progress = state.value.progress
            if (progress < 0.20f) {
                navigate(WordResultFragmentDirections.actionResultWordFragmentToFlashCardFragment())
            } else if (progress < 0.30f) {
                navigate(WordResultFragmentDirections.actionResultWordFragmentToFindMeaningQuizFragment())
            } else if (progress < 0.50f) {
                navigate(WordResultFragmentDirections.actionResultWordFragmentToReverseMeaningQuizFragment())
            } else if (progress < 0.70f) {
                navigate(WordResultFragmentDirections.actionResultWordFragmentToSentenceQuizFragment())
            } else if (progress < 0.90f) {
                navigate(WordResultFragmentDirections.actionResultWordFragmentToPuzzleQuizFragment())
            } else {
                nextCourse()
            }
        }
    }

    private fun nextCourse() {
        viewModelScope.launch {
            courseWordRepository.getNextCourse().collect {
                it?.let { course ->
                    updateCourse(course.id)
                }
            }
        }
    }

    private fun updateCourse(id: Long) {
        viewModelScope.launch {
            courseWordRepository.updateCourse(
                id = id,
                isStart = true,
                progress = 0f
            )
            delay(150)
            navigate(NavDeeplinkDestination.WordsDashboard)
        }
    }
}