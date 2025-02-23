package com.skyvo.mobile.top.words.feature.words.mearning

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.resource.SoundEffect
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.layout.AppShowAnswerCard
import com.skyvo.mobile.core.uikit.compose.picker.AppChooseItemComponent
import com.skyvo.mobile.core.uikit.compose.progressbar.AppLinearProgressbar
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.UI_EMPTY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindMeaningQuizFragment : BaseComposeFragment<FindMeaningQuizViewModel>() {

    override val viewModel: FindMeaningQuizViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: FindMeaningQuizViewModel) {

        val state by viewModel.state.collectAsStateWithLifecycle()

        if (state.nextCount == 1) {
            if (state.playSoundType == 0) {
                SoundEffect(requireContext()).playSuccess()
            } else {
                SoundEffect(requireContext()).playError()
            }
        }

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    if (state.isSingleQuiz) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = AppDimension.default.dp16,
                                    start = AppDimension.default.dp16,
                                    end = AppDimension.default.dp16
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppText(
                                modifier = Modifier.weight(1.4f),
                                text = "${state.questionIndex}/${state.wordIdListSize}",
                                style = AppTypography.default.body
                            )

                            AppLinearProgressbar(
                                modifier = Modifier
                                    .weight(10f),
                                maxStep = 10f,
                                currentStep = state.questionIndex.toFloat()*0.8f
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1.3f)
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
                    } else {
                        AppTopHeader(title = String.UI_EMPTY) {
                            viewModel.next(isBack = true)
                        }
                    }
                },
                bottomView = {
                    AppShowAnswerCard(
                        selectAnswer = state.selectAnswer,
                        correctWord = state.currentQuestion?.word,
                        visible = state.showAnswer
                    ) {
                        viewModel.nextQuestion()
                    }

                    if (state.nextCount != 1) {
                        AppPrimaryLargeButton(
                            text = stringResource(id = com.skyvo.mobile.core.resource.R.string.check_answer),
                            enabled = state.selectAnswer.isNullOrEmpty().not()
                        ) {
                            viewModel.nextQuestion()
                        }
                    }
                }
            ) {
                LazyColumn {
                    item {
                        AppText(
                            text = state.currentQuestion?.question.orEmpty(),
                            style = AppTypography.default.bodyExtraLargeBold,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    start = AppDimension.default.dp16,
                                    end = AppDimension.default.dp16,
                                    bottom = AppDimension.default.dp8
                                ),
                            textAlign = TextAlign.Center
                        )

                        AppText(
                            text = "(${state.currentQuestion?.questionTranslate})",
                            style = AppTypography.default.bodyExtraLarge,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    start = AppDimension.default.dp16,
                                    end = AppDimension.default.dp16,
                                    bottom = AppDimension.default.dp24
                                ),
                            color = LocalAppColor.current.colorTextSubtler,
                            textAlign = TextAlign.Center
                        )
                    }

                    state.currentQuestion?.let {
                        item {
                            it.answerList?.forEach { item ->
                                AppChooseItemComponent(
                                    modifier = Modifier.padding(
                                        horizontal = AppDimension.default.dp16,
                                        vertical = AppDimension.default.dp8
                                    ),
                                    backgroundColor = LocalAppColor.current.colorBackgroundSelected,
                                    text = item.label.orEmpty(),
                                    isSelected = state.selectAnswer == item.label
                                ) {
                                    viewModel.selectAnswer(item.label.orEmpty(), item.isCorrect)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}