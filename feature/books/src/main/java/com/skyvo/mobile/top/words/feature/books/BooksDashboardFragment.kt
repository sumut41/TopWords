package com.skyvo.mobile.top.words.feature.books

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
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
        viewModel: BooksDashboardViewModel
    ) {
        AppPrimaryTheme (
            navigationBarColor = LocalAppColor.current.colorBottomMenu
        ) {
            AppScaffold {
                LazyColumn {
                    item {
                        AppText(text = "BooksDashboardFragment")
                    }
                }
            }
        }
    }
}