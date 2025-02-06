package com.skyvo.mobile.top.words.feature.books.search

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.manager.AppBookClickWordItem
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.book.BookRepository
import com.skyvo.mobile.core.shared.extension.convertJsonToList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : BaseComposeViewModel<BooksSearchUIState>() {

    override fun setInitialState(): BooksSearchUIState {
        return BooksSearchUIState()
    }

    private fun searchBook(text: String?) {
        viewModelScope.launch {
            bookRepository.search(text).collect {
                it?.let { bookList ->
                    val searchBooks = bookList.map { book ->
                        AppBook(
                            id = book.id,
                            title = book.title.orEmpty(),
                            content = book.content.orEmpty(),
                            contentTr = book.contentTr.orEmpty(),
                            imageUrl = book.imageUrl.orEmpty(),
                            isNew = book.isNew,
                            level = book.level.orEmpty(),
                            min = book.min.orEmpty(),
                            genre = book.genre.orEmpty(),
                            words = book.words.convertJsonToList<AppBookClickWordItem>()
                        )
                    }

                    setState {
                        copy(
                            books = searchBooks
                        )
                    }
                }
            }
        }
    }

    fun searchBooks(searchText: String) {
        if (searchText.isNotEmpty()) {
            searchBook(searchText)
        }
    }
}