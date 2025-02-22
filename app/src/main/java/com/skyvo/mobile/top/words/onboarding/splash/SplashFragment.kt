package com.skyvo.mobile.top.words.onboarding.splash

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SplashFragment : BaseComposeFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = false

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView()
        }
    }

    private fun forceConfiguration(languageCode: String) {
        try {
            val locale = Locale(languageCode)
            val config = requireContext().resources.configuration
            config.setLocale(locale)
            requireContext().resources.updateConfiguration(
                config,
                requireContext().resources.displayMetrics
            )
        } catch (ex: Exception) {
            recordException(ex)
        }
    }

    @Composable
    private fun ContentView() {
        val state by viewModel.state.collectAsStateWithLifecycle()

        when(state.nativeLanguageCode) {
            "tr" -> forceConfiguration("tr")
            "de" -> forceConfiguration("de")
            "az" -> forceConfiguration("az")
            "it" -> forceConfiguration("it")
        }

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
                            style = AppTypography.default.logoTextStyle
                        )
                    }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        text = "Top Language Words",
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = AppDimension.default.dp24),
                        textAlign = TextAlign.Center,
                        color = LocalAppColor.current.colorTextMain,
                        style = AppTypography.default.bigLargeTitleBold
                    )
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