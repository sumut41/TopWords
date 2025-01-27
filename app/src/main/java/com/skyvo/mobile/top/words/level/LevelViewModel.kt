package com.skyvo.mobile.top.words.level

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.CustomerLevelList
import com.skyvo.mobile.core.base.manager.FiDataManager
import com.skyvo.mobile.core.base.manager.Level
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(
    private val userManager: UserManager,
    private val fiDataManager: FiDataManager
) : BaseComposeViewModel<LevelUIState>() {

    override fun setInitialState(): LevelUIState {
        return LevelUIState()
    }

    fun setLevelList(list: ArrayList<Level>) {
        setState {
            copy(
                levelList = list
            )
        }

        if (state.value.selectLevelList.isEmpty()) {
            val first: HashMap<String?, Level> = hashMapOf()
            first.set(
                key = list.first().type,
                value = list.first()
            )
            setState {
                copy(
                    selectLevelList = first,
                    selectLevelString = list.first().type
                )
            }
        }
    }

    fun selectLevel(level: Level, isRemove: Boolean = false) {
        val news = state.value.selectLevelList

        if (isRemove) {
            news.remove(level.type)
        } else {
            news.set(
                key = level.type,
                value = level
            )
        }
        setState {
            copy(
                selectLevelList = news,
                selectLevelString = news.keys.joinToString(",")
            )
        }
    }

    fun next() {
        state.value.selectLevelList.let { list ->
            userManager.customerLevelList = CustomerLevelList(
                levelList = list.values.toCollection(ArrayList())
            )
            viewModelScope.launch {
                delay(300)
                fiDataManager.fetch { isComplete ->
                    if (isComplete) {
                        navigate(NavDeeplinkDestination.WordsDashboard)
                    } else {
                        showErrorMessage()
                    }
                }
            }
        }
    }
}