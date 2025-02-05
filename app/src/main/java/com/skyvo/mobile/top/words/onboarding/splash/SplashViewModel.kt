package com.skyvo.mobile.top.words.onboarding.splash

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.firebase.RemoteConfigManager
import com.skyvo.mobile.core.base.manager.FiDataManager
import com.skyvo.mobile.core.base.manager.LevelType
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.word.WordEntity
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.core.uikit.theme.ThemeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userManager: UserManager,
    private val fiDataManager: FiDataManager,
    private val remoteConfigManager: RemoteConfigManager,
    private val wordRepository: WordRepository
) : BaseComposeViewModel<SplashUIState>() {

    override fun setInitialState(): SplashUIState {
        return SplashUIState()
    }

    init {
        ThemeUtils.setAppTheme(isNightMode = userManager.isDarkTheme)
        remoteConfigManager.initRemoteConfig {
            if (userManager.learnLanguage != null &&
                userManager.nativeLanguage != null
            ) {
                getData()
            } else {
                startTimer()
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            delay(300)
            fiDataManager.fetch { isComplete ->
                if (isComplete) {
                    viewModelScope.launch {
                        setLocaleData()
                    }
                }
            }
        }
    }

    private suspend fun setLocaleData() {
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
                        translateList = item.translateList?.joinToString {","}
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
                        translateList = item.translateList?.joinToString {","}
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
                        translateList = item.translateList?.joinToString {","}
                    )
                )
            }
            wordRepository.insertWordList(wordList)
            startTimer()
        }
    }

    private fun startTimer() {
        val timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if (userManager.learnLanguage != null &&
                    userManager.nativeLanguage != null
                ) {
                    navigate(NavDeeplinkDestination.WordsDashboard)
                } else {
                    navigate(SplashFragmentDirections.actionSplashFragmentToStartScreenFragment())
                }
            }
        }
        timer.start()
    }
} 