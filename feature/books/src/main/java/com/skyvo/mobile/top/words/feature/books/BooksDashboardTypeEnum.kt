package com.skyvo.mobile.top.words.feature.books

import androidx.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    BooksDashboardTypeEnum.BEGINNER,
    BooksDashboardTypeEnum.INTERMEDIATE,
    BooksDashboardTypeEnum.ADVANCED
)
annotation class BooksDashboardTypeEnum {
    companion object {
        const val BEGINNER = 0
        const val INTERMEDIATE = 1
        const val ADVANCED = 2
    }
}