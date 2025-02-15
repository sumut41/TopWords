package com.skyvo.mobile.top.words.feature.words.status

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.database.course.CourseWordEntity

data class StatusUIState (
    val currentCourse: CourseWordEntity? = null,
    val isWordCardCompleted: Boolean = false,
    val isWriteQuizCompleted: Boolean = false,
    val isTranslateQuizCompleted: Boolean = false,
    val isBlankFillQuizCompleted: Boolean = false
): UIState