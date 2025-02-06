package com.skyvo.mobile.top.words.level

import com.skyvo.mobile.core.base.viewmodel.UIState

data class LevelGoalUIState (
    val goalList: List<Int> = listOf(),
    val buttonEnable: Boolean = false,
    val selectGoalMin: Int? = null
): UIState