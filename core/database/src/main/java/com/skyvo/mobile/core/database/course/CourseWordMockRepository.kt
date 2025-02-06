package com.skyvo.mobile.core.database.course

class CourseWordMockRepository: CourseWordRepository(
    CourseMockDao()
)