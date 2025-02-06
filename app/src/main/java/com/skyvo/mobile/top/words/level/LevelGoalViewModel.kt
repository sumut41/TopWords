package com.skyvo.mobile.top.words.level

import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LevelGoalViewModel@Inject constructor(
    private val userManager: UserManager
) : BaseComposeViewModel<LevelGoalUIState>() {

    override fun setInitialState(): LevelGoalUIState {
        return LevelGoalUIState()
    }

    init {
        setLevelList()
    }

    private fun setLevelList() {
        setState {
            copy(
                goalList = listOf(5, 10, 15, 20),
                buttonEnable = false
            )
        }
    }

    fun selectGoal(goal: Int) {
        setState {
            copy(
                selectGoalMin = goal,
                buttonEnable = true
            )
        }
    }

    fun next() {
        state.value.selectGoalMin.let { min ->
            userManager.goalMinute = min
            navigate(LevelGoalFragmentDirections.actionLevelGoalFragmentToDataLoaderFragment())
        }
    }
}