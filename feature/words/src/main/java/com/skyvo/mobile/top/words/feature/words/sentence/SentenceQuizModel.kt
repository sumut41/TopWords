package com.skyvo.mobile.top.words.feature.words.sentence

import com.skyvo.mobile.core.base.manager.AppWordTranslateItem

data class SentenceQuizModel (
    val word: String? = null,
    val question: String? = null,
    val questionTranslate: String? = null,
    val answerList: List<AppWordTranslateItem>? = null
)