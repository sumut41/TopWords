package com.skyvo.mobile.top.words.feature.menu

import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.uikit.theme.ThemeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val userManager: UserManager
) : BaseComposeViewModel<MenuUIState>() {

    override fun setInitialState(): MenuUIState {
        return MenuUIState()
    }

    init {
        getUserDefaults()
    }

    private fun getUserDefaults() {
        setState {
            copy(
                isDarkTheme = userManager.isDarkTheme
            )
        }
    }

    fun changeTheme() {
        val isDark = state.value.isDarkTheme.not()
        ThemeUtils.setAppTheme(isNightMode = isDark)
        setState {
            copy(
                isDarkTheme = isDark
            )
        }
        userManager.isDarkTheme = isDark
    }
}