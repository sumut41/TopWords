package com.skyvo.mobile.top.words.level

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.Level
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
import com.skyvo.mobile.core.uikit.util.GetLevelColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelFragment : BaseComposeFragment<LevelViewModel>() {

    override val viewModel: LevelViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: LevelViewModel) {
        createLevelList(viewModel)
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopLongHeader(
                        title = stringResource(com.skyvo.mobile.core.resource.R.string.select_level_page_title, state.learningLanguage.orEmpty()),
                        onBackClickListener = {
                            navigateBack()
                        }
                    )
                },
                bottomView = {
                    AppPrimaryLargeButton(
                        text = stringResource(com.skyvo.mobile.core.resource.R.string.continue_button),
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

                    items(state.levelList) { level ->
                        AppChooseItemComponent(
                            modifier = Modifier.padding(
                                horizontal = AppDimension.default.dp16,
                                vertical = AppDimension.default.dp8
                            ),
                            text = level.name.orEmpty(),
                            isSelected = level == state.selectLevel,
                            startContent = {
                                Box(
                                    modifier = Modifier
                                        .size(
                                            width = 36.dp,
                                            height = 28.dp
                                        )
                                        .background(
                                            color = GetLevelColor(level.type.orEmpty()),
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AppText(
                                        text = level.type.orEmpty(),
                                        style = AppTypography.default.bodyLarge,
                                        color = Color.White
                                    )
                                }
                            },
                            onSelectListener = { _ ->
                                viewModel.selectLevel(
                                    level
                                )
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

    private fun createLevelList(viewModel: LevelViewModel) {
        val list: ArrayList<Level> = arrayListOf()

        list.add(
            Level(
                type = "A1",
                name = requireActivity().getString(com.skyvo.mobile.core.resource.R.string.level_beginner)
            )
        )

        list.add(
            Level(
                type = "A2",
                name = requireActivity().getString(com.skyvo.mobile.core.resource.R.string.level_elementary)
            )
        )

        list.add(
            Level(
                type = "B1",
                name = requireActivity().getString(com.skyvo.mobile.core.resource.R.string.level_intermediate)
            )
        )

        list.add(
            Level(
                type = "B2",
                name = requireActivity().getString(com.skyvo.mobile.core.resource.R.string.level_upper)
            )
        )

        list.add(
            Level(
                type = "C1",
                name = requireActivity().getString(com.skyvo.mobile.core.resource.R.string.level_advanced)
            )
        )

        list.add(
            Level(
                type = "C2",
                name = requireActivity().getString(com.skyvo.mobile.core.resource.R.string.level_proficient)
            )
        )

        viewModel.setLevelList(list)
    }

    @Preview
    @Composable
    private fun Preview() {
        val vm = LevelViewModel(
            UserMockManager()
        )
        ContentView(vm)
    }
}