package com.skyvo.mobile.top.words.feature.books

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.manager.AppBookClickWordItem
import com.skyvo.mobile.core.base.manager.LevelType
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.book.BookRepository
import com.skyvo.mobile.core.shared.extension.convertJsonToList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksDashboardViewModel @Inject constructor(
    private val userManager: UserManager,
    private val bookRepository: BookRepository
) : BaseComposeViewModel<BooksDashboardUIState>() {

    private var beginnerBookList: List<AppBook>? = null
    private var intermediateBookList: List<AppBook>? = null
    private var advancedBookList: List<AppBook>? = null

    override fun setInitialState(): BooksDashboardUIState {
        return BooksDashboardUIState()
    }

    init {
        getBeginnerBookList()
    }

    private fun getBeginnerBookList() {
        viewModelScope.launch {
            bookRepository.getBookList(
                level = LevelType.BEGINNER.key,
                languageCode = userManager.learnLanguage?.code.orEmpty()
            ).collect { bookList ->
                beginnerBookList = bookList?.map {
                    AppBook(
                        id = it.id,
                        title = it.title.orEmpty(),
                        content = it.content.orEmpty(),
                        contentTr = it.contentTr.orEmpty(),
                        imageUrl = it.imageUrl.orEmpty(),
                        isNew = it.isNew,
                        level = it.level.orEmpty(),
                        min = it.min.orEmpty(),
                        genre = it.genre.orEmpty(),
                        words = it.words?.convertJsonToList<AppBookClickWordItem>()
                    )
                }
                setState {
                    copy(
                        selectedTabIndex = 0,
                        showBookList = beginnerBookList
                    )
                }
                getIntermediateBookList()
            }
        }
    }

    private fun getIntermediateBookList() {
        viewModelScope.launch {
            bookRepository.getBookList(
                level = LevelType.INTERMEDIATE.key,
                languageCode = userManager.learnLanguage?.code.orEmpty()
            ).collect { bookList ->
                intermediateBookList = bookList?.map {
                    AppBook(
                        id = it.id,
                        title = it.title.orEmpty(),
                        content = it.content.orEmpty(),
                        contentTr = it.contentTr.orEmpty(),
                        imageUrl = it.imageUrl.orEmpty(),
                        isNew = it.isNew,
                        level = it.level.orEmpty(),
                        min = it.min.orEmpty(),
                        genre = it.genre.orEmpty(),
                        words = it.words?.convertJsonToList<AppBookClickWordItem>()
                    )
                }
                getAdvancedBookList()
            }
        }
    }

    private fun getAdvancedBookList() {
        viewModelScope.launch {
            bookRepository.getBookList(
                level = LevelType.ADVANCED.key,
                languageCode = userManager.learnLanguage?.code.orEmpty()
            ).collect { bookList ->
                advancedBookList = bookList?.map {
                    AppBook(
                        id = it.id,
                        title = it.title.orEmpty(),
                        content = it.content.orEmpty(),
                        contentTr = it.contentTr.orEmpty(),
                        imageUrl = it.imageUrl.orEmpty(),
                        isNew = it.isNew,
                        level = it.level.orEmpty(),
                        min = it.min.orEmpty(),
                        genre = it.genre.orEmpty(),
                        words = it.words?.convertJsonToList<AppBookClickWordItem>()
                    )
                }
            }
        }
    }

    fun updateTabIndex(index: Int) {
        setState {
            copy(
                selectedTabIndex = index,
                showBookList = when (index) {
                    BooksDashboardTypeEnum.BEGINNER -> beginnerBookList
                    BooksDashboardTypeEnum.INTERMEDIATE -> intermediateBookList
                    else -> advancedBookList
                }
            )
        }
    }
}