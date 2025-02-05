package com.skyvo.mobile.top.words.feature.books.search

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.FiDataManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.top.words.feature.books.model.BooksItem
import com.skyvo.mobile.top.words.feature.books.model.KeyValueItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksSearchViewModel @Inject constructor(
    private val bookManager: FiDataManager
) : BaseComposeViewModel<BooksSearchUIState>() {

    private var remoteBooks: List<BooksItem>? = emptyList()

    override fun setInitialState(): BooksSearchUIState {
        return BooksSearchUIState()
    }

    override fun fetchExtras(extra: Bundle) {
        super.fetchExtras(extra)
        with(BooksSearchFragmentArgs.fromBundle(extra)) {
            val args = this
            getBooks(args.bookType)
            setState {
                copy(
                    bookType = args.bookType
                )
            }
        }

    }

    private fun getBooks(type: Int) {
        viewModelScope.launch {
            val books = when (type) {
                0 -> bookManager.getBeginnerBookList()
                1 -> bookManager.getIntermediateBookList()
                2 -> bookManager.getAdvancedBookList()
                else -> emptyList()
            }?.map {
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
            remoteBooks = books
            setState {
                copy(
                    books = books.orEmpty()
                )
            }
        }
    }

    fun searchBooks(searchText: String) {
        if (searchText.isNotEmpty()) {
            setState {
                copy(
                    books = remoteBooks?.filter { book ->
                        book.title?.contains(searchText.lowercase(), ignoreCase = true) == true
                    } ?: emptyList()
                )
            }
        } else {
            setState {
                copy(
                    books = remoteBooks.orEmpty()
                )
            }
        }
    }
}