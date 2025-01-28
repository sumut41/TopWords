package com.skyvo.mobile.top.words.feature.books

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.FiDataManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.top.words.feature.books.model.BooksItem
import com.skyvo.mobile.top.words.feature.books.model.KeyValueItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksDashboardViewModel @Inject constructor(
    private val bookManager: FiDataManager
) : BaseComposeViewModel<BooksDashboardUIState>() {

    override fun setInitialState(): BooksDashboardUIState {
        return BooksDashboardUIState()
    }

    init {
        viewModelScope.launch {
            val beginnerBooks = bookManager.getBeginnerBookList()?.map {
                BooksItem(
                    title = it.title.orEmpty(),
                    content = it.content.orEmpty(),
                    contentTr = it.contentTr.orEmpty(),
                    imageUrl = it.imageUrl.orEmpty(),
                    isNew = it.isNew,
                    level = it.level.orEmpty(),
                    min = it.min.orEmpty(),
                    genre = it.genre.orEmpty(),
                    words = it.words?.map { word ->
                        KeyValueItem(
                            key = word.key ?: "",
                            value = word.value ?: ""
                        )
                    } ?: listOf()
                )
            }
            val intermediateBooks = bookManager.getIntermediateBookList()?.map {
                BooksItem(
                    title = it.title.orEmpty(),
                    content = it.content.orEmpty(),
                    contentTr = it.contentTr.orEmpty(),
                    imageUrl = it.imageUrl.orEmpty(),
                    isNew = it.isNew,
                    level = it.level.orEmpty(),
                    min = it.min.orEmpty(),
                    genre = it.genre.orEmpty(),
                    words = it.words?.map { word ->
                        KeyValueItem(
                            key = word.key ?: "",
                            value = word.value ?: ""
                        )
                    } ?: listOf()
                )
            }
            val advancedBooks = bookManager.getAdvancedBookList()?.map {
                BooksItem(
                    title = it.title.orEmpty(),
                    content = it.content.orEmpty(),
                    contentTr = it.contentTr.orEmpty(),
                    imageUrl = it.imageUrl.orEmpty(),
                    isNew = it.isNew,
                    level = it.level.orEmpty(),
                    min = it.min.orEmpty(),
                    genre = it.genre.orEmpty(),
                    words = it.words?.map { word ->
                        KeyValueItem(
                            key = word.key ?: "",
                            value = word.value ?: ""
                        )
                    } ?: listOf()
                )
            }
            setState {
                copy(
                    beginnerBookList = beginnerBooks,
                    intermediateBookList = intermediateBooks,
                    advancedBookList = advancedBooks
                )
            }
        }
    }

    fun updateTabIndex(index: Int) {
        setState {
            copy(
                selectedTabIndex = index
            )
        }
    }
}