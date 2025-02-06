package com.skyvo.mobile.top.words.feature.words

import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.database.course.CourseWordEntity

data class WordsDashboardUIState(
    val learnLanguage: Language? = null,
    val wordList: List<String>? = null,
    val selectedTabIndex: Int = 0,
    val currentCourse: CourseWordEntity? = null
)