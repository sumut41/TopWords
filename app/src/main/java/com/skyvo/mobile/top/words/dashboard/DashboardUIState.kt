package com.skyvo.mobile.top.words.dashboard

import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.base.viewmodel.UIState

data class DashboardUIState(
    val nativeLanguage: Language? = null,
    val learnLanguage: Language? = null
) : UIState 