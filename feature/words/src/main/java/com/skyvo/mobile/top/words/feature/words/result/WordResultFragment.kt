package com.skyvo.mobile.top.words.feature.words.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.database.course.CourseWordMockRepository
import com.skyvo.mobile.core.resource.SoundEffect
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.resource.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordResultFragment : BaseComposeFragment<WordResultViewModel>() {

    override val viewModel: WordResultViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: WordResultViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            SoundEffect(requireContext()).playCompleted()
        }

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    Row (
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                top = AppDimension.default.dp16,
                                end = AppDimension.default.dp24
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(start = AppDimension.default.dp24)
                                .size(AppDimension.default.dp32)
                                .clickable {
                                    navigateBack()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                           // empty
                        }

                        Box(
                            modifier = Modifier
                                .padding(start = AppDimension.default.dp24)
                                .size(AppDimension.default.dp32)
                                .background(
                                    color = LocalAppColor.current.colorIconBackground,
                                    shape = RoundedCornerShape(AppDimension.default.dp10)
                                )
                                .clickable {
                                    navigateBack()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            AppIcon(
                                modifier = Modifier.size(AppDimension.default.dp20),
                                imageVector = ImageVector.vectorResource(com.skyvo.mobile.core.uikit.R.drawable.ic_close),
                                tint = LocalAppColor.current.colorIcon,
                                contentDescription = "Theme"
                            )
                        }
                    }
                },
                bottomView = {
                    AppPrimaryLargeButton(
                        text = stringResource(com.skyvo.mobile.core.resource.R.string.continue_button)
                    ) {
                        viewModel.next()
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    ConfettiAnimation(
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (state.isSingleQuiz) {
                            LessonAnimation(
                                modifier = Modifier
                                    .padding(top = AppDimension.default.dp16)
                                    .size(250.dp)
                            )
                        } else {
                            SuccessAnimation(
                                modifier = Modifier
                                    .padding(top = AppDimension.default.dp40)
                                    .size(210.dp)
                            )
                        }

                        AppText(
                            text = when(state.progress) {
                                0.20f -> stringResource(R.string.result_first_title)
                                0.40f -> stringResource(R.string.result_second_title)
                                0.60f -> stringResource(R.string.result_thirt_title)
                                0.80f -> stringResource(R.string.result_fourth_title)
                                1f -> stringResource(R.string.result_finale_title)
                                else -> stringResource(R.string.result_default_title)
                            },
                            modifier = Modifier
                                .padding(top = AppDimension.default.dp48),
                            textAlign = TextAlign.Center,
                            style = AppTypography.default.bodyExtraLargeBold,
                            color = LocalAppColor.current.colorTextMain
                        )

                        AppText(
                            text = when(state.progress) {
                                0.20f -> stringResource(R.string.result_first_message)
                                0.40f -> stringResource(R.string.result_second_message)
                                0.60f -> stringResource(R.string.result_thirt_message)
                                0.80f -> stringResource(R.string.result_fourth_message)
                                1f -> stringResource(R.string.result_finale_message)
                                else -> stringResource(R.string.result_default_message)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = AppDimension.default.dp16,
                                    start = AppDimension.default.dp16,
                                    end = AppDimension.default.dp16
                                ),
                            style = AppTypography.default.bodyPrimary,
                            color = LocalAppColor.current.colorTextSubtler,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun LessonAnimation(modifier: Modifier = Modifier) {
        val preloaderLottieComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                com.skyvo.mobile.core.resource.R.raw.lesson_complete
            )
        )

        val preloaderProgress by animateLottieCompositionAsState(
            preloaderLottieComposition,
            iterations = 4,
            isPlaying = true,
            restartOnPlay = false,
        )


        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = modifier
        )
    }

    @Composable
    fun SuccessAnimation(modifier: Modifier = Modifier) {
        val preloaderLottieComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                com.skyvo.mobile.core.resource.R.raw.success
            )
        )

        val preloaderProgress by animateLottieCompositionAsState(
            preloaderLottieComposition,
            iterations = 3,
            isPlaying = true,
            restartOnPlay = false,
        )


        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = modifier
        )
    }

    @Composable
    fun ConfettiAnimation(modifier: Modifier = Modifier) {
        val preloaderLottieComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                com.skyvo.mobile.core.resource.R.raw.confitti
            )
        )

        val preloaderProgress by animateLottieCompositionAsState(
            preloaderLottieComposition,
            isPlaying = true
        )


        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = modifier
        )
    }

    @Composable
    @Preview
    private fun Preview() {
        val vm = WordResultViewModel(
            UserMockManager(),
            CourseWordMockRepository()
        )
        ContentView(vm)
    }
}