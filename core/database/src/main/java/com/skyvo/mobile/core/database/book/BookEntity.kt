package com.skyvo.mobile.core.database.book

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skyvo.mobile.core.database.RoomDatabaseConstant

@Entity(tableName = RoomDatabaseConstant.BOOK_TABLE)
data class BookEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryLevel: String? = null,
    val content: String? = null,
    val contentTr: String? = null,
    val title: String? = null,
    val imageUrl: String? = null,
    val isNew: Boolean? = false,
    val level: String? = null,
    val languageCode: String? = null,
    val min: String? = null,
    val genre: String? = null,
    val words: String? = null
)