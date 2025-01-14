package com.skyvo.mobile.core.base.manager

import androidx.annotation.DrawableRes

data class Language(
    val code: String,
    val name: String,
    @DrawableRes val icon: Int
)