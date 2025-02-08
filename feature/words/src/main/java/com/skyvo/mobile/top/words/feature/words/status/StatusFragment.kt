package com.skyvo.mobile.top.words.feature.words.status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.database.course.CourseWordMockRepository
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.layout.AppVerticalDivider
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatusFragment : BaseComposeFragment<StatusViewModel>() {

    override val viewModel: StatusViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: StatusViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopHeader(
                        title = "Course ${state.currentCourse?.id}"
                    ) {
                        navigateBack()
                    }
                },
                bottomView = {
                    AppPrimaryLargeButton(
                        text = "Continue"
                    ) {
                        viewModel.next()
                    }
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    StepView(
                        text = "Learn New Words",
                        isCompleted = state.isWordCardCompleted
                    )

                    StepView(
                        text = "Fill Blanks",
                        isCompleted = state.isBlankFillQuizCompleted
                    )

                    StepView(
                        text = "Find Meaning",
                        isCompleted = state.isWordQuizCompleted
                    )

                    StepView(
                        text = "Sentence Puzzle",
                        isCompleted = state.isSentenceQuizCompleted,
                        dividerVisible = false
                    )

                    AppSpacer(
                        height = AppDimension.default.dp128
                    )
                }
            }
        }
    }

    @Composable
    private fun StepView(
        icon: Int = com.skyvo.mobile.core.uikit.R.drawable.ic_dummble,
        text: String? = null,
        isCompleted: Boolean = false,
        dividerVisible: Boolean = true
    ) {
        Column(
            modifier = Modifier
                .width(260.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(AppDimension.default.dp48)
                        .background(
                            color = if (isCompleted) LocalAppColor.current.primary else LocalAppColor.current.colorSurfaceBase,
                            shape = CircleShape
                        )
                        .border(
                            width = AppDimension.default.dp1,
                            color = if (isCompleted) LocalAppColor.current.primary else LocalAppColor.current.colorBorder,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AppIcon(
                        modifier = Modifier
                            .size(AppDimension.default.dp24),
                        imageVector = ImageVector.vectorResource(icon),
                        tint = if (isCompleted) Color.White else LocalAppColor.current.colorIcon,
                        contentDescription = text
                    )
                }

                AppText(
                    text = text.orEmpty(),
                    modifier = Modifier
                        .padding(start = AppDimension.default.dp16),
                    style = AppTypography.default.bodyLargeNormal,
                    color = if (isCompleted) LocalAppColor.current.colorTextMain else LocalAppColor.current.colorTextSubtler
                )
            }

            if (dividerVisible) {
                AppVerticalDivider(
                    modifier = Modifier
                        .padding(start = AppDimension.default.dp24),
                    border = AppDimension.default.dp2,
                    height = AppDimension.default.dp32,
                    color = if (isCompleted) LocalAppColor.current.primary else LocalAppColor.current.colorBorder
                )
            }
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        val vm = StatusViewModel(
            UserMockManager(),
            CourseWordMockRepository()
        )
        ContentView(vm)
    }
}