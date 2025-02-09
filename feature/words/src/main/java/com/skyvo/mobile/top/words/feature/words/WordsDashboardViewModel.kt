package com.skyvo.mobile.top.words.feature.words

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.shared.enum.DayStatus
import com.skyvo.mobile.core.shared.extension.getWeekDayAbbreviations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
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
                learnLanguage = userManager.learnLanguage,
                weekDayList = userManager.nativeLanguage?.code.getWeekDayAbbreviations(),
                weekDayStatus = getWeeklyAttendanceStatus()
            )
        }
        getCurrentCourse()
    }

    private fun getWeeklyAttendanceStatus(): List<Int> {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_WEEK)
        val attendance = userManager.getWeeklyAttendance()

        // Calendar.SUNDAY = 1, Calendar.MONDAY = 2, ... Calendar.SATURDAY = 7
        // Bizim istediğimiz: Pazartesi = 0, Salı = 1, ... Pazar = 6
        val adjustedToday = if (today == Calendar.SUNDAY) 6 else today - 2

        val statusList = (0..6).map { dayIndex ->
            when {
                dayIndex == adjustedToday -> DayStatus.TODAY.status
                dayIndex > adjustedToday -> DayStatus.UPCOMING.status
                attendance.contains(dayIndex + 2) -> DayStatus.COMPLETED.status
                else -> DayStatus.MISSED.status
            }
        }

        // Kaçırılan gün sayısını hesapla ve state'e kaydet
        val missedDays = statusList.count { it == DayStatus.MISSED.status }
        setState {
            copy(
                missedDaysCount = missedDays
            )
        }

        return statusList
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