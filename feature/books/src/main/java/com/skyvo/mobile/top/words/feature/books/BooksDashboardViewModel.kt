package com.skyvo.mobile.top.words.feature.books

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
class BooksDashboardViewModel @Inject constructor(
    private val userManager: UserManager,
    private val bookRepository: BookRepository
) : BaseComposeViewModel<BooksDashboardUIState>() {

    private var beginnerBookList: List<AppBook>? = null

    override fun setInitialState(): BooksDashboardUIState {
        return BooksDashboardUIState()
    }

    init {
        getAllBooks()
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            bookRepository.getBookAllList(
                languageCode = userManager.learnLanguage?.code.orEmpty()
            ).collect { bookList ->
                beginnerBookList = bookList?.map {
                    AppBook(
                        id = it.id,
                        title = it.title.orEmpty(),
                        content = it.content.orEmpty(),
                        contentTranslate = it.contentTr.orEmpty(),
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
                        showBookList = beginnerBookList
                    )
                }
            }
        }
    }
}