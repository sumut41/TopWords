package com.skyvo.mobile.core.database.course

class CourseMockDao: CourseWordDao {
    override suspend fun insertAll(words: List<CourseWordEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCourseList(): List<CourseWordEntity>? {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentCourse(): CourseWordEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun getFirstCourse(): CourseWordEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun updateCourse(
        isStart: Boolean,
        progress: Float,
        isComplete: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getCompletedCourseCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getInProgressCourseCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun clearAll() {
        TODO("Not yet implemented")
    }
}