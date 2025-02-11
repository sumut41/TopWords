package com.skyvo.mobile.top.words.level

import com.skyvo.mobile.core.base.manager.Level
import com.skyvo.mobile.core.base.viewmodel.UIState

data class LevelUIState (
    val levelList: List<Level> = listOf(),
    val buttonEnable: Boolean = false,
    val selectLevel: Level? = null,
    val learningLanguage: String? = null
): UIState