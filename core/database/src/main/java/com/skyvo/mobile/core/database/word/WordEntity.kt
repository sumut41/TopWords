package com.skyvo.mobile.core.database.word

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skyvo.mobile.core.database.RoomDatabaseConstant

@Entity(tableName = RoomDatabaseConstant.WORD_TABLE)
data class WordEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val languageCode: String? = null,
    val level: String? = null,
    val word: String? = null,
    val translate: String? = null,
    val quiz: String? = null,
    val quizTranslate: String? = null,
    val translateList: String? = null,
    val isKnow: Boolean = false,
    val isNotMuch: Boolean? = null,
    var isFavorite: Boolean = false
)