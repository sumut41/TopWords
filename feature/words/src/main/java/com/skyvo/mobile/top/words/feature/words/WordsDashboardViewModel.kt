package com.skyvo.mobile.top.words.feature.words

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsDashboardViewModel @Inject constructor(
    private val userManager: UserManager,
    private val courseWordRepository: CourseWordRepository
) : BaseComposeViewModel<WordsDashboardUIState>() {

    override fun setInitialState(): WordsDashboardUIState {
        return WordsDashboardUIState()
    }

    init {
        setState {
            copy(
                learnLanguage = userManager.learnLanguage
            )
        }
        getCurrentCourse()
    }

    fun getCurrentCourse() {
        viewModelScope.launch {
            courseWordRepository.getCurrentCourse().collect {
                it?.let { course ->
                    setState {
                        copy(
                            currentCourse = course
                        )
                    }
                } ?: kotlin.run {
                    getFirstCourse()
                }
            }
        }
    }

    private fun getFirstCourse() {
        viewModelScope.launch {
            courseWordRepository.getFirstCourse().collect {
                it?.let { course ->
                    setState {
                        copy(
                            currentCourse = course
                        )
                    }
                    updateCourse()
                }
            }
        }
    }

    private fun updateCourse() {
        viewModelScope.launch {
            courseWordRepository.updateCourse(
                isStart = true,
                progress = 0f
            )
        }
    }

    fun updateTabIndex(index: Int) {
        setState {
            copy(
                selectedTabIndex = index
            )
        }
    }
}