package com.skyvo.mobile.core.database.course

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skyvo.mobile.core.database.RoomDatabaseConstant

@Entity(tableName = RoomDatabaseConstant.COURSE_WORD_TABLE)
data class CourseWordEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val level: String? = null,
    val wordIds: String? = null,
    val isCompleted: Boolean = false,
    val progress: Float = 0.0f,
    val isStart: Boolean = false
)