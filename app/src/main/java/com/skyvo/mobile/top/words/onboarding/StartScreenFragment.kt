package com.skyvo.mobile.top.words.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartScreenFragment : BaseComposeFragment<StartScreenViewModel>() {

    override val viewModel: StartScreenViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = false

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView()
        }
    }

    @Composable
    private fun ContentView() {
        AppPrimaryTheme {
            AppScaffold(
                backgroundColor = LocalAppColor.current.colorSurfaceBase,
                bottomView = {
                    AppPrimaryLargeButton(
                        text = "Get started",
                        icon = com.skyvo.mobile.core.uikit.R.drawable.ic_lightning,
                        onClick = { /* TODO: Navigate to next screen */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = AppDimension.default.dp8)
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = AppDimension.default.dp16),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.onboarding_one),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    )

                    Spacer(modifier = Modifier.height(AppDimension.default.dp32))

                    AppText(
                        text = "Learn a language easily with cards",
                        style = AppTypography.default.bigLargeTitleBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = AppDimension.default.dp16)
                    )

                    AppText(
                        text = "Learn words using cards, choosing levels that are convenient for you",
                        textAlign = TextAlign.Center,
                        color = LocalAppColor.current.colorTextSubtler,
                        modifier = Modifier.fillMaxWidth()
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