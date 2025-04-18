package com.skyvo.mobile.top.words.onboarding.splash

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.firebase.RemoteConfigManager
import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.manager.AppBookParentModel
import com.skyvo.mobile.core.base.manager.LevelType
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.book.BookEntity
import com.skyvo.mobile.core.database.book.BookRepository
import com.skyvo.mobile.core.shared.exception.ExceptionFBHelper.Companion.recordException
import com.skyvo.mobile.core.shared.extension.toJson
import com.skyvo.mobile.core.shared.extension.toModel
import com.skyvo.mobile.core.uikit.theme.ThemeUtils
import com.skyvo.mobile.top.words.constant.GlobalConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userManager: UserManager,
    private val remoteConfigManager: RemoteConfigManager,
    private val bookRepository: BookRepository
) : BaseComposeViewModel<SplashUIState>() {

    private var beginnerBookList: List<AppBook>? = listOf()
    private var intermediateBookList: List<AppBook>? = listOf()

    override fun setInitialState(): SplashUIState {
        return SplashUIState()
    }

    init {
        ThemeUtils.setAppTheme(isNightMode = userManager.isDarkTheme)
        userManager.checkAndUpdateWeeklyAttendance()
        setState {
            copy(
                nativeLanguageCode = userManager.nativeLanguage?.code
            )
        }
        if (userManager.refreshVersion > 0) {
            remoteConfigManager.initRemoteConfig {
                if (remoteConfigManager.getLong(GlobalConstant.REFRESH_VERSION) > userManager.refreshVersion) {
                    fetchRemoteConfigData {
                        saveBook()
                    }
                } else {
                    startTimer()
                }
            }
        } else {
            startTimer()
        }
    }

    private fun fetchRemoteConfigData(onComplete: (Boolean) -> Unit) {
        try {
            val nativeLanguageCode = userManager.nativeLanguage?.code
            val learnLanguageCode = userManager.learnLanguage?.code

            beginnerBookList = remoteConfigManager.getString(
                "books_beginner_${nativeLanguageCode}_$learnLanguageCode"
            ).toModel<AppBookParentModel>()?.books

            intermediateBookList = remoteConfigManager.getString(
                "books_intermediate_${nativeLanguageCode}_$learnLanguageCode"
            ).toModel<AppBookParentModel>()?.books

            userManager.refreshVersion = remoteConfigManager.getLong(GlobalConstant.REFRESH_VERSION)
            onComplete.invoke(true)
        } catch (ex: Exception) {
            onComplete.invoke(false)
        }
    }

    private fun saveBook() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                bookRepository.clearAll()
                delay(100)
                val allBooks = (beginnerBook().orEmpty() + intermediateBook().orEmpty())
                bookRepository.insertAll(allBooks)
                userManager.isCompletedSetup = true

                withContext(Dispatchers.Main) {
                    startTimer()
                }
            } catch (e: Exception) {
                recordException(e)
            }
        }
    }

    private fun beginnerBook(): List<BookEntity>? {
        return beginnerBookList?.map { book ->
            BookEntity(
                categoryLevel = LevelType.BEGINNER.key,
                level = book.level,
                languageCode = userManager.learnLanguage?.code,
                content = book.content,
                contentTr = book.contentTranslate,
                title = book.title,
                min = book.min,
                genre = book.genre,
                isNew = book.isNew,
                imageUrl = book.imageUrl,
                words = book.words.toJson()
            )
        }
    }

    private fun intermediateBook(): List<BookEntity>? {
        return intermediateBookList?.map { book ->
            BookEntity(
                categoryLevel = LevelType.INTERMEDIATE.key,
                level = book.level,
                languageCode = userManager.learnLanguage?.code,
                content = book.content,
                contentTr = book.contentTranslate,
                title = book.title,
                min = book.min,
                genre = book.genre,
                isNew = book.isNew,
                imageUrl = book.imageUrl,
                words = book.words.toJson()
            )
        }
    }

    private fun startTimer() {
        val timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if (userManager.isCompletedSetup) {
                    navigate(NavDeeplinkDestination.WordsDashboard)
                } else {
                    navigate(SplashFragmentDirections.actionSplashFragmentToNaturalLanguageFragment())
                }
            }
        }
        timer.start()
    }
} 