package com.skyvo.mobile.top.words.feature.words

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.uikit.compose.layout.AppWordCard
import com.skyvo.mobile.core.uikit.compose.layout.AppWordStepType
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
            AppScaffold(
                backgroundColor = LocalAppColor.current.colorSecondarySurface
            ) {
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
                        AppText(
                            text = "STEP 1",
                            style = AppTypography.default.bodyBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = AppDimension.default.dp16,
                                    top = AppDimension.default.dp8,
                                    bottom = AppDimension.default.dp8
                                )
                        )
                    }

                    item {
                        AppWordCard(
                            stepType = AppWordStepType.STUDY,
                            title = "New 5 words",
                            subTitle = "Learn 5 new words with fun",
                            isActive = true
                        )
                        AppWordCard(
                            stepType = AppWordStepType.QUIZ,
                            title = "Start Quiz",
                            subTitle = "Start Quiz with fun",
                            isActive = true
                        )
                        AppWordCard(
                            stepType = AppWordStepType.HARD,
                            title = "Start Video",
                            subTitle = "Start Quiz with fun"
                        )
                    }

                    item {
                        AppText(
                            text = "STEP 2",
                            style = AppTypography.default.bodyBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = AppDimension.default.dp16,
                                    top = AppDimension.default.dp16,
                                    bottom = AppDimension.default.dp8
                                )
                        )
                    }

                    item {
                        AppWordCard(
                            stepType = AppWordStepType.STUDY,
                            title = "New 5 words",
                            subTitle = "Learn 5 new words with fun"
                        )
                        AppWordCard(
                            stepType = AppWordStepType.QUIZ,
                            title = "Start Quiz",
                            subTitle = "Start Quiz with fun",
                        )
                        AppWordCard(
                            stepType = AppWordStepType.HARD,
                            title = "Start Video",
                            subTitle = "Start Quiz with fun",
                        )
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