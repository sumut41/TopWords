package com.skyvo.mobile.top.words.onboarding.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.indicator.AppPageIndicator
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.top.words.onboarding.intro.views.IntroFirstScreen
import com.skyvo.mobile.top.words.onboarding.intro.views.IntroSecondScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartScreenFragment : BaseComposeFragment<StartScreenViewModel>() {

    override val viewModel: StartScreenViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = false

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: StartScreenViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        val pageCount = 2
        val pagerState = rememberPagerState(
            pageCount = { pageCount }
        )

        AppPrimaryTheme {
            AppScaffold(
                backgroundColor = LocalAppColor.current.colorSurfaceBase,
                header = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppDimension.default.dp24)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(start = AppDimension.default.dp24)
                                .size(AppDimension.default.dp32)
                                .background(
                                    color = LocalAppColor.current.colorIconBackground,
                                    shape = RoundedCornerShape(AppDimension.default.dp10)
                                )
                                .clickable {
                                    viewModel.changeTheme()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            AppIcon(
                                modifier = Modifier.size(AppDimension.default.dp20),
                                imageVector = if (state.isDarkTheme) {
                                    ImageVector.vectorResource(R.drawable.ic_sunny)
                                } else {
                                    ImageVector.vectorResource(R.drawable.ic_night)
                                },
                                tint = LocalAppColor.current.colorIcon,
                                contentDescription = "Theme"
                            )
                        }
                    }
                },
                bottomView = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppPageIndicator(
                            modifier = Modifier.padding(bottom = AppDimension.default.dp32),
                            currentPosition = pagerState.currentPage,
                            pageCount = pagerState.pageCount
                        )

                        AppPrimaryLargeButton(
                            text = if (pagerState.currentPage == pageCount - 1) "Get started" else "Next",
                            icon = if (pagerState.currentPage == pageCount - 1) R.drawable.ic_lightning else null,
                            onClick = {
                                if (pagerState.currentPage == pageCount - 1) {
                                    navigate(StartScreenFragmentDirections.actionStartScreenFragmentToNaturalLanguageFragment())
                                } else {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage.plus(1))
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = AppDimension.default.dp8)
                        )
                    }
                }
            ) {
                LazyColumn {
                    item {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            when (it) {
                                0 -> IntroFirstScreen(state.isDarkTheme)
                                1 -> IntroSecondScreen(state.isDarkTheme)
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        val vm = StartScreenViewModel(
            UserMockManager()
        )
        ContentView(vm)
    }
} 