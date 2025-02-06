package com.skyvo.mobile.top.words.level

import com.skyvo.mobile.core.base.manager.Level
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(
    private val userManager: UserManager
) : BaseComposeViewModel<LevelUIState>() {

    override fun setInitialState(): LevelUIState {
        return LevelUIState()
    }

    fun setLevelList(list: ArrayList<Level>) {
        setState {
            copy(
                levelList = list,
                buttonEnable = false
            )
        }
    }

    fun selectLevel(level: Level) {
        setState {
            copy(
                selectLevel = level,
                buttonEnable = true
            )
        }
    }

    fun next() {
        state.value.selectLevel.let { level ->
            userManager.customerLevel = level
            navigate(LevelFragmentDirections.actionLevelFragmentToLevelGoalFragment())
        }
    }
}