package com.skyvo.mobile.top.words.onboarding.splash

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.util.forceConfiguration
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseComposeFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = false

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView()
        }
    }



    @Composable
    private fun ContentView() {
        val state by viewModel.state.collectAsStateWithLifecycle()

        requireContext().forceConfiguration(state.nativeLanguageCode.orEmpty())

        AppPrimaryTheme {
            AppScaffold(
                backgroundColor = LocalAppColor.current.colorSurfaceBase,
                bottomView = {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = AppDimension.default.dp8),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppText(
                            text = "SKYVO",
                            textAlign = TextAlign.Center,
                            color = LocalAppColor.current.primary,
                            style = AppTypography.default.goldman
                        )
                    }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppText(
                            text = "TOP WORDS",
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = AppDimension.default.dp24),
                            textAlign = TextAlign.Center,
                            color = LocalAppColor.current.colorTextMain,
                            style = AppTypography.default.logoText
                        )
                        AppText(
                            text = stringResource(com.skyvo.mobile.core.resource.R.string.learning_english),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = LocalAppColor.current.colorTextSubtler,
                            style = AppTypography.default.bodyLarge
                        )
                    }
                }
            }
        }
    }

    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES
    )
    @Composable
    private fun Preview() {
        ContentView()
    }
} 