package com.skyvo.mobile.top.words.feature.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.bottomsheet.AppOptionBottomSheet
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.layout.AppBookCard
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.compose.widget.Book
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import com.skyvo.mobile.core.uikit.theme.AppDarkColors
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppLightColors
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksDashboardFragment : BaseComposeFragment<BooksDashboardViewModel>() {
    override val viewModel: BooksDashboardViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = true

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    fun ContentView(
        viewModel: BooksDashboardViewModel,
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        BooksDashboardView(state)
    }

    @Composable
    fun BooksDashboardView(
        state: BooksDashboardUIState,
    ) {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        var showFilterBottomSheet by remember { mutableStateOf(false) }

        AppPrimaryTheme(
            navigationBarColor = if (isSystemInDarkTheme().not()) {
                AppLightColors.colorBottomMenu
            } else {
                AppDarkColors.colorBottomMenu
            }
        ) {
            AppScaffold (
                header = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = AppDimension.default.dp8
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AppText(
                                text = stringResource(com.skyvo.mobile.core.resource.R.string.books_title),
                                style = AppTypography.default.bodyExtraLargeBold,
                                modifier = Modifier.padding(
                                    vertical = AppDimension.default.dp8,
                                    horizontal = AppDimension.default.dp16
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .padding(end = AppDimension.default.dp16)
                                    .size(AppDimension.default.dp32)
                                    .background(
                                        color = LocalAppColor.current.colorIconBackground,
                                        shape = RoundedCornerShape(AppDimension.default.dp10)
                                    )
                                    .clickable {
                                        showFilterBottomSheet = true
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                AppIcon(
                                    modifier = Modifier.size(AppDimension.default.dp20),
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_filter),
                                    tint = LocalAppColor.current.colorIcon,
                                    contentDescription = stringResource(com.skyvo.mobile.core.resource.R.string.filter)
                                )
                            }
                        }
                    }
                },
                paddingValues = PaddingValues(top = AppDimension.default.dp56)
            ) {
                LazyColumn {
                    item {
                        AppSpacer(
                            height = AppDimension.default.dp8
                        )
                    }

                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .height(screenHeight * 0.90f)
                                .padding(horizontal = AppDimension.default.dp8),
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(
                                items = state.showBookList.orEmpty(),
                                key = { book -> book.id ?: 0 }
                            ) { book ->
                                AppBookCard(
                                    book = Book(
                                        contentEn = book.content.orEmpty(),
                                        contentTr = book.contentTranslate.orEmpty(),
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
                                    )
                                ) {
                                    navigate(
                                        BooksDashboardFragmentDirections.actionBooksDashboardFragmentToBookDetailFragment(
                                            book.id ?: 0
                                        )
                                    )
                                }
                            }
                        }
                    }

                    item{
                        AppSpacer(height = AppDimension.default.dp56)
                    }
                }
            }

            if (showFilterBottomSheet) {
                AppOptionBottomSheet(
                    title = stringResource(com.skyvo.mobile.core.resource.R.string.filter_title),
                    options = getFilterList(),
                    selectedIndex = state.selectFilterIndex,
                    onDismiss = {
                        showFilterBottomSheet = false
                    }
                ) {
                    viewModel.updateFilterIndex(it)
                }
            }
        }
    }

    private fun getFilterList(): List<String> {
        return listOf(
            requireActivity().getString(com.skyvo.mobile.core.resource.R.string.filter_book_all),
            requireActivity().getString(com.skyvo.mobile.core.resource.R.string.filter_book_beginner),
            requireActivity().getString(com.skyvo.mobile.core.resource.R.string.filter_book_intermediate),
            requireActivity().getString(com.skyvo.mobile.core.resource.R.string.filter_book_advanced)
        )
    }
}