package com.skyvo.mobile.core.base.manager

data class Level(
    val type: String?,
    val name: String?
)

enum class LevelType(val key: String?) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced")
}