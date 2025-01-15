package com.skyvo.mobile.top.words.dashboard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseComposeFragment<DashboardViewModel>() {

    override val viewModel: DashboardViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = true

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView()
        }
    }

    @Composable
    private fun ContentView() {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            AppScaffold {
                LazyColumn {
                    item {
                        AppText(
                            text = "Welcome to Dashboard",
                            style = AppTypography.default.bodyExtraLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        AppText(
                            text = "Your native language is: ${state.nativeLanguage?.name}",
                            style = AppTypography.default.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        AppText(
                            text = "You want to learn: ${state.learnLanguage?.name}",
                            style = AppTypography.default.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
} 