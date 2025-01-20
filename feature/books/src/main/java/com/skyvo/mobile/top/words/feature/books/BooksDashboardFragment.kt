package com.skyvo.mobile.top.words.feature.books

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.tabrow.AppTabRow
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
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
            navigationBarColor = LocalAppColor.current.colorBottomMenu
        ) {
            AppScaffold {
                Column {
                    AppTabRow(
                        selectedIndex = state.selectedTabIndex,
                        items = tabTitles,
                        onSelectionChange = {
                            viewModel.updateTabIndex(it)
                        }
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