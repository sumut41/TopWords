package com.skyvo.mobile.top.words.feature.words.sentence

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.database.word.WordEntity

data class SentenceArrangeUIState(
    val originalSentence: String = "",
    val displayWords: List<String> = emptyList(),
    val questionSentences: List<WordEntity>? = null,
    val questionIndex: Int = 0,
    val showAnswer: Boolean = false,
    val userAnswer: String = "",
    val correctAnswer: String = "",
    val wordList: List<String> = emptyList(),
    val filledWords: List<String?> = emptyList(),
    val selectedWords: Set<String> = emptySet(),
    val correctOrder: List<String> = emptyList(),
    val selectedIndices: List<Int> = emptyList(),
    val wordToPositionMap: Map<String, Int> = emptyMap(),
    val isCompleted: Boolean = false
) : UIState 