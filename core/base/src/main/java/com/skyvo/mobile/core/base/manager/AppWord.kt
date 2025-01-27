package com.skyvo.mobile.core.base.manager

data class AppWordParentModel(
    val wordList: List<AppWord>? = null
)

data class AppWord (
    val word: String? = null,
    val translate: String? = null,
    val quiz: String? = null,
    val quizTranslate: String? = null,
    val translateList: List<AppWordTranslateItem>? = null
)

data class AppWordTranslateItem(
    val label: String? = null,
    val isCorrect: Boolean? = false
)