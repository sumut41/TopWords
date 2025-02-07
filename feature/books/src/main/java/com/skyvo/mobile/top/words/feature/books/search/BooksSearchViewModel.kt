package com.skyvo.mobile.top.words.feature.books.search

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.manager.AppBookClickWordItem
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.book.BookRepository
import com.skyvo.mobile.core.shared.extension.convertJsonToList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val userManager: UserManager
) : BaseComposeViewModel<BooksSearchUIState>() {

    private var remoteBookList : List<AppBook>? = null

    override fun setInitialState(): BooksSearchUIState {
        return BooksSearchUIState()
    }

    override fun fetchExtras(extra: Bundle) {
        super.fetchExtras(extra)
        with(BooksSearchFragmentArgs.fromBundle(extra)) {
            val args = this
            searchBook(args.bookType)
        }
    }
    private fun searchBook(level: String?) {
        viewModelScope.launch {
            bookRepository.getBookList(level.orEmpty(), userManager.learnLanguage?.code.orEmpty()).collect {
                it?.let { bookList ->
                    val books = bookList.map { book ->
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
                    remoteBookList = books

                    setState {
                        copy(
                            books = books
                        )
                    }
                }
            }
        }
    }

    fun searchBooks(searchText: String) {
        if (searchText.isNotEmpty()) {
            setState {
                copy(
                    books = remoteBookList?.filter { book ->
                        book.title?.contains(searchText.lowercase(), ignoreCase = true) == true
                    } ?: emptyList()
                )
            }
        } else {
            setState {
                copy(
                    books = remoteBookList.orEmpty()
                )
            }
        }
    }
}