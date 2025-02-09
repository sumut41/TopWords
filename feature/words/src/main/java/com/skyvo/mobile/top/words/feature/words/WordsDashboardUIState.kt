package com.skyvo.mobile.top.words.feature.words

import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.database.course.CourseWordEntity

data class WordsDashboardUIState(
    val learnLanguage: Language? = null,
    val currentCourse: CourseWordEntity? = null,
    val selectedTabIndex: Int = 0,
    val weekDayList: List<String> = emptyList(),
    val weekDayStatus: List<Int> = emptyList(),
    val missedDaysCount: Int = 0
)