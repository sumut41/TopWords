package com.skyvo.mobile.top.words.feature.words.result

import com.skyvo.mobile.core.base.viewmodel.UIState

data class WordResultUIState (
    val language: String? = null,
    val isSingleQuiz: Boolean = false,
    val courseId: Long = 1,
    val progress: Float = 0f
): UIState