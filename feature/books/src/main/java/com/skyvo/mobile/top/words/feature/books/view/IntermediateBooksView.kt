package com.skyvo.mobile.top.words.feature.books.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skyvo.mobile.core.uikit.compose.layout.AppBookCard
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.widget.Book
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.util.setTextColor
import com.skyvo.mobile.top.words.feature.books.BooksDashboardUIState
import com.skyvo.mobile.top.words.feature.books.model.BooksItem

@Composable
fun IntermediateBooksView(
    state: BooksDashboardUIState,
    navigateBookDetail: (BooksItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.intermediateBookList ?: emptyList()) { book ->
            AppBookCard(
                book = Book(
                    contentEn = book.content.orEmpty(),
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
                    genre = book.genre.orEmpty(),
                    min = book.min,
                    isNew = book.isNew ?: false
                ),
                levelColor = setTextColor(book.level)
            ) {
                navigateBookDetail(book)
            }
        }
        item {
            AppSpacer(height = AppDimension.default.dp56)
        }
    }
}