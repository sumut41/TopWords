package com.skyvo.mobile.core.database.course

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class CourseWordRepository @Inject constructor(
    private val courseWordDao: CourseWordDao
) {
    suspend fun insertAll(list: List<CourseWordEntity>) {
        courseWordDao.insertAll(list)
    }

    fun getAllCourseList(): Flow<List<CourseWordEntity>?> = flow {
        emit(courseWordDao.getAllCourseList())
    }.flowOn(Dispatchers.IO)

    fun getCurrentCourse(): Flow<CourseWordEntity?> = flow {
        emit(courseWordDao.getCurrentCourse())
    }.flowOn(Dispatchers.IO)

    fun getFirstCourse(): Flow<CourseWordEntity?> = flow {
        emit(courseWordDao.getFirstCourse())
    }.flowOn(Dispatchers.IO)

    suspend fun updateCourse(isStart: Boolean, progress: Float) {
        courseWordDao.updateCourse(isStart, progress, progress >= 1f)
    }

    fun getCompletedCourseCount(): Flow<Int> = flow {
        emit(courseWordDao.getCompletedCourseCount())
    }.flowOn(Dispatchers.IO)

    fun getInProgressCourseCount(): Flow<Int> = flow {
        emit(courseWordDao.getInProgressCourseCount())
    }.flowOn(Dispatchers.IO)

    suspend fun clearAll() {
        courseWordDao.clearAll()
    }
}