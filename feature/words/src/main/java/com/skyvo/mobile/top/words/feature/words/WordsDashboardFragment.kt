package com.skyvo.mobile.top.words.feature.words

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsDashboardFragment : BaseComposeFragment<WordsDashboardViewModel>() {

    override val viewModel: WordsDashboardViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = true

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    fun ContentView(
        viewModel: WordsDashboardViewModel
    ) {
        AppPrimaryTheme {
            AppScaffold {
                LazyColumn {
                    item {
                        AppText(text = "WordsDashboardFragment")
                    }
                }
            }
        }
    }
}