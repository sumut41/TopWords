package com.skyvo.mobile.top.words.feature.books.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skyvo.mobile.core.uikit.compose.layout.AppBookCard
import com.skyvo.mobile.core.uikit.compose.widget.Book
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import com.skyvo.mobile.core.uikit.util.setTextColor
import com.skyvo.mobile.top.words.feature.books.BooksDashboardUIState
import com.skyvo.mobile.top.words.feature.books.model.BooksItem

@Composable
fun AdvancedBooksView(
    state: BooksDashboardUIState,
    navigateBookDetail: (BooksItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.advancedBookList ?: emptyList()) { book ->
            AppBookCard(
                book = Book(
                    contentEn = book.contentEn.orEmpty(),
                    contentTr = book.contentTr.orEmpty(),
                    words = book.words?.map {
                        KeyValue(
                            key = it.key.orEmpty(),
                            value = it.value.orEmpty()
                        )
                    },
                    imageUrl = book.imageUrl.orEmpty(),
                    title = book.title.orEmpty(),
                    level = book.level.orEmpty(),
                    genre = book.genre.orEmpty()
                ),
                levelColor = setTextColor(book.level)
            ) {
                navigateBookDetail(book)
            }
        }
    }
}