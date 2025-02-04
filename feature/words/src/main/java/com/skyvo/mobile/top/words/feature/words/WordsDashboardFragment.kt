package com.skyvo.mobile.top.words.feature.words

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.uikit.compose.button.AppPrimarySmallButton
import com.skyvo.mobile.core.uikit.compose.progressbar.AppCircleProgressbar
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDarkColors
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppLightColors
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
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
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme(
            navigationBarColor = if (isSystemInDarkTheme().not()) {
                AppLightColors.colorBottomMenu
            } else {
                AppDarkColors.colorBottomMenu
            }
        ) {
            AppScaffold {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = AppDimension.default.dp8,
                                    horizontal = AppDimension.default.dp16
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppText(
                                text = "WORDS",
                                style = AppTypography.default.bodyExtraLargeBold
                            )

                            state.learnLanguage?.let {
                                Image(
                                    modifier = Modifier.size(
                                        width = 24.dp,
                                        height = 16.dp
                                    ),
                                    imageVector = ImageVector.vectorResource(it.icon),
                                    contentDescription = it.name
                                )
                            }
                        }
                    }

                    item {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = AppDimension.default.dp24,
                                    start = AppDimension.default.dp16,
                                    end = AppDimension.default.dp16
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AppText(
                                text = "Active Level",
                                style = AppTypography.default.subTitleBold
                            )

                            AppText(
                                text = "See Course",
                                style = AppTypography.default.bodyUnderline,
                                color = LocalAppColor.current.primary
                            )
                        }
                    }

                    item {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = AppDimension.default.dp16)
                                .background(
                                    color = LocalAppColor.current.background,
                                    shape = RoundedCornerShape(AppDimension.default.dp10)
                                )
                                .border(
                                    width = 1.dp,
                                    color = LocalAppColor.current.colorBorder,
                                    shape = RoundedCornerShape(AppDimension.default.dp10)
                                )
                                .clip(RoundedCornerShape(AppDimension.default.dp10))
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AppCircleProgressbar(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(
                                            end = AppDimension.default.dp8,
                                            start = AppDimension.default.dp16
                                        ),
                                    diameter = 80.dp,
                                    progress = 0.1f
                                )

                                Column (
                                    modifier = Modifier
                                        .weight(2.5f)
                                ) {
                                    AppText(
                                        text = "Chapter 1",
                                        style = AppTypography.default.body,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                top = AppDimension.default.dp16,
                                                start = AppDimension.default.dp16,
                                                end = AppDimension.default.dp16,
                                                bottom = AppDimension.default.dp8
                                            ),
                                        color = LocalAppColor.current.colorTextSubtler
                                    )

                                    AppText(
                                        text = "Discovering English",
                                        style = AppTypography.default.bodyLarge,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = AppDimension.default.dp16,
                                                end = AppDimension.default.dp16,
                                                bottom = AppDimension.default.dp8
                                            )
                                    )

                                    AppPrimarySmallButton(
                                        text = "Continue",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = AppDimension.default.dp16,
                                                end = AppDimension.default.dp16,
                                                bottom = AppDimension.default.dp16
                                            )
                                    ) {
                                        navigate(WordsDashboardFragmentDirections.actionWordsDashboardFragmentToFlashCardFragment())
                                    }
                                }
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
        val vm = WordsDashboardViewModel(
            UserMockManager()
        )
        ContentView(vm)
    }
}