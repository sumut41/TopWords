package com.skyvo.mobile.top.words.feature.words.status

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val userManager: UserManager,
    private val courseWordRepository: CourseWordRepository
): BaseComposeViewModel<StatusUIState>() {

    override fun setInitialState(): StatusUIState {
        return StatusUIState()
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
                }
            }
        }
    }
}