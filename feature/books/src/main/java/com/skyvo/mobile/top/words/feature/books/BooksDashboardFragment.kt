package com.skyvo.mobile.top.words.feature.books

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.tabrow.AppTabRow
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDarkColors
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppLightColors
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.top.words.feature.books.view.AdvancedBooksView
import com.skyvo.mobile.top.words.feature.books.view.BeginnerBooksView
import com.skyvo.mobile.top.words.feature.books.view.IntermediateBooksView
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
        val tabTitles = listOf("Beginner", "Intermediate", "Advanced")

        AppPrimaryTheme (
            navigationBarColor = if (isSystemInDarkTheme().not()) {
                AppLightColors.colorBottomMenu
            } else {
                AppDarkColors.colorBottomMenu
            }
        ) {
            AppScaffold {
                Column {
                    AppText(
                        text = "BOOKS",
                        style = AppTypography.default.bodyExtraLargeBold,
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                vertical = AppDimension.default.dp8,
                                horizontal = AppDimension.default.dp16
                            )
                    )

                    AppTabRow(
                        selectedIndex = state.selectedTabIndex,
                        items = tabTitles,
                        onSelectionChange = {
                            viewModel.updateTabIndex(it)
                        }
                    )

                    AppSpacer(
                        height = AppDimension.default.dp8
                    )

                    when (state.selectedTabIndex) {
                        BooksDashboardTypeEnum.BEGINNER -> BeginnerBooksView(
                            state = state,
                            navigateBookDetail = { book ->
                                navigate(
                                    BooksDashboardFragmentDirections.actionBooksDashboardFragmentToBookDetailFragment(
                                        book
                                    )
                                )
                            }
                        )
                        BooksDashboardTypeEnum.INTERMEDIATE -> IntermediateBooksView(
                            state = state,
                            navigateBookDetail = { book ->
                                navigate(
                                    BooksDashboardFragmentDirections.actionBooksDashboardFragmentToBookDetailFragment(
                                        book
                                    )
                                )
                            }
                        )
                        BooksDashboardTypeEnum.ADVANCED -> AdvancedBooksView(
                            state = state,
                            navigateBookDetail = { book ->
                                navigate(
                                    BooksDashboardFragmentDirections.actionBooksDashboardFragmentToBookDetailFragment(
                                        book
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}