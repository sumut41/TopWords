package com.skyvo.mobile.top.words.feature.books.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.layout.AppBookCard
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.textfield.AppSearchTextField
import com.skyvo.mobile.core.uikit.compose.widget.Book
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.ghostClickable
import com.skyvo.mobile.core.uikit.util.setTextColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksSearchFragment : BaseComposeFragment<BooksSearchViewModel>() {
    override val viewModel: BooksSearchViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        return composeView.setContent {
            ContentView(viewModel = viewModel)
        }
    }

    @Composable
    fun ContentView(
        viewModel: BooksSearchViewModel
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        BooksSearchView(state = state)
    }

    @Composable
    fun BooksSearchView(
        state: BooksSearchUIState
    ) {
        var searchText by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopHeader(
                        title = "Book Search"
                    ) {
                        navigateBack()
                    }
                }
            ) {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .ghostClickable {
                            focusManager.clearFocus()
                        }
                ) {
                    item {
                        AppSpacer(
                            height = AppDimension.default.dp16
                        )
                    }

                    item {
                        AppSearchTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = AppDimension.default.dp16),
                            value = searchText,
                            placeholder = "Search",
                            onSearch = {
                                searchText = it
                                viewModel.searchBooks(it)
                            },
                            backgroundColor = LocalAppColor.current.colorTabBackgroundColor,
                            borderColor = LocalAppColor.current.colorBorder,
                            focusedBorderColor = LocalAppColor.current.colorBorderFocused
                        )
                    }

                    item {
                        AppSpacer(
                            height = AppDimension.default.dp8
                        )
                    }

                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .height(screenHeight * 0.85f)
                                .padding(horizontal = AppDimension.default.dp8),
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(
                                items = state.books,
                                key = { book -> book.id ?: 0 }
                            ) { book ->
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
                                    navigate(
                                        BooksSearchFragmentDirections.actionBooksSearchFragmentToBookDetailFragment(
                                            book.id ?: 0
                                        )
                                    )
                                }
                            }
                        }
                    }

                    item {
                        AppSpacer(height = AppDimension.default.dp56)
                    }
                }
            }
        }
    }
}