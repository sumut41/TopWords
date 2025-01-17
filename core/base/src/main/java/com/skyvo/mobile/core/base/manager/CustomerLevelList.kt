package com.skyvo.mobile.core.base.manager

data class CustomerLevelList (
    val levelList: ArrayList<Level>?
)

data class Level(
    val type: String?,
    val name: String?
)