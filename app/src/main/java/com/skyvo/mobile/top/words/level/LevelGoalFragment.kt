package com.skyvo.mobile.top.words.level


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.header.AppTopLongHeader
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.picker.AppChooseItemComponent
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.util.GetGoalColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelGoalFragment : BaseComposeFragment<LevelGoalViewModel>() {

    override val viewModel: LevelGoalViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: LevelGoalViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopLongHeader(
                        title = "What's your daily learning goal?",
                        onBackClickListener = {
                            navigateBack()
                        }
                    )
                },
                bottomView = {
                    AppPrimaryLargeButton(
                        text = "Start learning",
                        icon = com.skyvo.mobile.core.uikit.R.drawable.ic_book,
                        onClick = {
                            viewModel.next()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = state.buttonEnable
                    )
                }
            ) {
                LazyColumn {
                    item {
                        AppSpacer(
                            height = AppDimension.default.dp48
                        )
                    }

                    items(state.goalList) { item ->
                        AppChooseItemComponent(
                            modifier = Modifier.padding(
                                horizontal = AppDimension.default.dp16,
                                vertical = AppDimension.default.dp8
                            ),
                            text = "${item} min/day",
                            isSelected = item == state.selectGoalMin,
                            startContent = {
                                Box(
                                    modifier = Modifier
                                        .size(
                                            width = 72.dp,
                                            height = 30.dp
                                        )
                                        .background(
                                            color = GetGoalColor(item),
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AppText(
                                        text = if (item <= 5) {
                                            "Casual"
                                        } else if (item <= 10) {
                                            "Regular"
                                        } else if (item <= 15) {
                                            "Serious"
                                        } else if (item <= 20) {
                                            "Intence"
                                        } else {
                                            "Normaly"
                                        },
                                        style = AppTypography.default.bodySmallBold,
                                        color = Color.White
                                    )
                                }
                            },
                            onSelectListener = { _ ->
                                viewModel.selectGoal(item)
                            }
                        )
                    }

                    item {
                        AppSpacer(
                            height = AppDimension.default.dp100
                        )
                    }
                }
            }
        }
    }


    @Preview
    @Composable
    private fun Preview() {
        val vm = LevelGoalViewModel(
            UserMockManager()
        )
        ContentView(vm)
    }
}