package com.skyvo.mobile.top.words.feature.words.puzzle

import com.skyvo.mobile.core.base.viewmodel.UIState

data class PuzzleQuizUIState (
    val courseId: Long? = null,
    val currentQuestion: PuzzleModel? = null,
    val wordList: List<PuzzleModel>? = null,
    val buttonEnable: Boolean = false,
    val showAnswer: Boolean = false,
    val selectAnswer: String? = null,
    val selectIndex: Int = 0,
    val nextCount: Int = 0,
    val correctCount: Int = 0,
    val unCorrectCount: Int = 0,
    val playSoundType: Int = 0
): UIState