package com.skyvo.mobile.top.words.feature.words

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.shared.enum.DayStatus
import com.skyvo.mobile.core.uikit.compose.button.AppPrimarySmallButton
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
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

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentCourse()
    }

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
                        Row(
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
                                text = state.currentCourse?.level.orEmpty(),
                                style = AppTypography.default.body,
                                color = LocalAppColor.current.colorTextSubtler
                            )
                        }
                    }

                    item {
                        Column(
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
                            Row(
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
                                    progress = state.currentCourse?.progress ?: 0f
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(2.5f)
                                ) {
                                    AppText(
                                        text = "Chapter ${state.currentCourse?.id}",
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
                                        navigate(WordsDashboardFragmentDirections.actionWordsDashboardFragmentToStatusFragment())
                                    }
                                }
                            }
                        }
                    }

                    item {
                        AppSpacer(
                            height = AppDimension.default.dp16
                        )
                        Row (
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    horizontal = AppDimension.default.dp16,
                                    vertical = AppDimension.default.dp8
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AppText(
                                text = "Week Challenge",
                                style = AppTypography.default.subTitleBold
                            )
                            AppText(
                                text = if (state.missedDaysCount > 0) {
                                    "${state.missedDaysCount} missed days"
                                } else {
                                    "No missed days"
                                },
                                style = AppTypography.default.body,
                                color = LocalAppColor.current.colorTextSubtler
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = AppDimension.default.dp16),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            state.weekDayList.forEachIndexed { index, day ->
                                DayItem(
                                    day = day,
                                    status = state.weekDayStatus.getOrNull(index)
                                        ?.let { status ->
                                            when (status) {
                                                DayStatus.COMPLETED.status -> DayStatus.COMPLETED
                                                DayStatus.TODAY.status -> DayStatus.TODAY
                                                DayStatus.MISSED.status -> DayStatus.MISSED
                                                else -> DayStatus.UPCOMING
                                            }
                                        } ?: DayStatus.UPCOMING
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun DayItem(
        day: String,
        status: DayStatus
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppText(
                text = day,
                style = AppTypography.default.bodySmall,
                color = LocalAppColor.current.colorTextSubtler
            )

            Box(
                modifier = Modifier
                    .padding(top = AppDimension.default.dp8)
                    .size(30.dp)
                    .background(
                        color = when (status) {
                            DayStatus.TODAY -> LocalAppColor.current.primary.copy(alpha = 0.2f)
                            DayStatus.COMPLETED -> LocalAppColor.current.colorSuccess.copy(alpha = 0.2f)
                            DayStatus.MISSED -> LocalAppColor.current.colorError.copy(alpha = 0.2f)
                            DayStatus.UPCOMING -> Color.Transparent
                        },
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = when (status) {
                            DayStatus.TODAY -> LocalAppColor.current.primary
                            DayStatus.COMPLETED -> LocalAppColor.current.colorSuccess
                            DayStatus.MISSED -> LocalAppColor.current.colorError
                            DayStatus.UPCOMING -> LocalAppColor.current.colorBorder
                        },
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                when (status) {
                    DayStatus.COMPLETED -> {
                        AppIcon(
                            modifier = Modifier.size(16.dp),
                            imageVector = ImageVector.vectorResource(com.skyvo.mobile.core.uikit.R.drawable.ic_check),
                            tint = LocalAppColor.current.colorSuccess,
                            contentDescription = "completed"
                        )
                    }

                    DayStatus.MISSED -> {
                        AppIcon(
                            modifier = Modifier.size(14.dp),
                            imageVector = ImageVector.vectorResource(com.skyvo.mobile.core.uikit.R.drawable.ic_close),
                            tint = LocalAppColor.current.colorError,
                            contentDescription = "missed"
                        )
                    }

                    DayStatus.TODAY -> {
                        AppIcon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(com.skyvo.mobile.core.uikit.R.drawable.ic_lightning),
                            tint = LocalAppColor.current.primary,
                            contentDescription = "missed"
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}