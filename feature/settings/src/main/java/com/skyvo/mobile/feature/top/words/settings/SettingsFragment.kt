package com.skyvo.mobile.feature.top.words.settings

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
class SettingsFragment: BaseComposeFragment<SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = true

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    fun ContentView(
        viewModel: SettingsViewModel
    ) {
        AppPrimaryTheme {
            AppScaffold {
                LazyColumn {
                    item {
                        AppText(text = "SettingsFragment")
                    }
                }
            }
        }
    }
}