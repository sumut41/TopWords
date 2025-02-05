package com.skyvo.mobile.top.words.level

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.FiDataManager
import com.skyvo.mobile.core.base.manager.Level
import com.skyvo.mobile.core.base.manager.LevelType
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.word.WordEntity
import com.skyvo.mobile.core.database.word.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(
    private val userManager: UserManager,
    private val fiDataManager: FiDataManager,
    private val wordRepository: WordRepository
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

            viewModelScope.launch {
                showLoading()
                delay(500)
                fiDataManager.fetch { isComplete ->
                    if (isComplete) {
                        setLocaleData()
                    } else {
                        dismissLoading {
                            showErrorMessage()
                        }
                    }
                }
            }
        }
    }

    private fun setLocaleData() {
        viewModelScope.launch {
            val wordList: ArrayList<WordEntity> = arrayListOf()
            //beginner
            fiDataManager.getBeginnerWordList()?.forEach { item ->
                wordList.add(
                    WordEntity(
                        word = item.word,
                        translate = item.translate,
                        quiz = item.quiz,
                        quizTranslate = item.quizTranslate,
                        level = LevelType.BEGINNER.key,
                        languageCode = userManager.learnLanguage?.code,
                        translateList = item.translateList?.joinToString { "," }
                    )
                )
            }
            //intermediate
            fiDataManager.getIntermediateWordList()?.forEach { item ->
                wordList.add(
                    WordEntity(
                        word = item.word,
                        translate = item.translate,
                        quiz = item.quiz,
                        quizTranslate = item.quizTranslate,
                        level = LevelType.INTERMEDIATE.key,
                        languageCode = userManager.learnLanguage?.code,
                        translateList = item.translateList?.joinToString { "," }
                    )
                )
            }
            //advanced
            fiDataManager.getAdvancedWordList()?.forEach { item ->
                wordList.add(
                    WordEntity(
                        word = item.word,
                        translate = item.translate,
                        quiz = item.quiz,
                        quizTranslate = item.quizTranslate,
                        level = LevelType.ADVANCED.key,
                        languageCode = userManager.learnLanguage?.code,
                        translateList = item.translateList?.joinToString { "," }
                    )
                )
            }
            wordRepository.insertWordList(wordList)
            dismissLoading {
                navigate(NavDeeplinkDestination.WordsDashboard)
            }
        }
    }
}