package com.skyvo.mobile.core.database.course

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skyvo.mobile.core.database.RoomDatabaseConstant

@Dao
interface CourseWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<CourseWordEntity>)

    @Query("SELECT * FROM ${RoomDatabaseConstant.COURSE_WORD_TABLE} ORDER BY id ASC")
    suspend fun getAllCourseList(): List<CourseWordEntity>?

    @Query("SELECT * FROM ${RoomDatabaseConstant.COURSE_WORD_TABLE} WHERE isStart = 1 AND isCompleted = 0 ORDER BY id ASC LIMIT 1")
    suspend fun getCurrentCourse(): CourseWordEntity?

    @Query("SELECT * FROM ${RoomDatabaseConstant.COURSE_WORD_TABLE} WHERE isStart = 0 AND isCompleted = 0 ORDER BY id ASC LIMIT 1")
    suspend fun getFirstCourse(): CourseWordEntity?

    @Query("UPDATE ${RoomDatabaseConstant.COURSE_WORD_TABLE} SET isStart = :isStart, progress = :progress, isCompleted = :isComplete")
    suspend fun updateCourse(isStart: Boolean, progress: Float, isComplete: Boolean)

    @Query("SELECT COUNT(*) FROM ${RoomDatabaseConstant.COURSE_WORD_TABLE} WHERE progress = 1.0 AND isCompleted = 1")
    suspend fun getCompletedCourseCount(): Int

    @Query("SELECT COUNT(*) FROM ${RoomDatabaseConstant.COURSE_WORD_TABLE} WHERE progress < 1.0 AND isCompleted = 0")
    suspend fun getInProgressCourseCount(): Int

    @Query("DELETE FROM ${RoomDatabaseConstant.COURSE_WORD_TABLE}")
    suspend fun clearAll()
}