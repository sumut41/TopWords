package com.skyvo.mobile.top.words.feature.words.mearning

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.top.words.feature.words.sentence.SentenceQuizModel

data class FindMeaningQuizUIState (
    val courseId: Long? = null,
    val selectIndex: Int = 0,
    val buttonEnable: Boolean = false,
    val showAnswer: Boolean = false,
    val selectAnswer: String? = null,
    val nextCount: Int = 0,
    val wordIdListSize: Int = 0,
    val currentQuestion: SentenceQuizModel? = null,
    val items: List<SentenceQuizModel>? = null,
    val correctCount: Int = 0,
    val unCorrectCount: Int = 0,
    val playSoundType: Int = 0,
    val isSingleQuiz: Boolean = false,
    val questionIndex: Int = 1
): UIState