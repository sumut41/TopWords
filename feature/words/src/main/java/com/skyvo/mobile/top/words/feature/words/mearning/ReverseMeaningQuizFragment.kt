package com.skyvo.mobile.top.words.feature.words.mearning

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.resource.R
import com.skyvo.mobile.core.resource.SoundEffect
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.layout.AppShowAnswerCard
import com.skyvo.mobile.core.uikit.compose.picker.AppChooseItemComponent
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.UI_EMPTY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReverseMeaningQuizFragment : BaseComposeFragment<ReverseMeaningQuizViewModel>() {

    override val viewModel: ReverseMeaningQuizViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun ContentView(viewModel: ReverseMeaningQuizViewModel) {

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
                    AppTopHeader(title = String.UI_EMPTY) {
                        viewModel.next(isBack = true)
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
                            text = stringResource(id = R.string.check_answer),
                            enabled = state.selectAnswer.isNullOrEmpty().not()
                        ) {
                            viewModel.nextQuestion()
                        }
                    }
                }
            ) {
                if (state.currentQuestion != null) {
                    AnimatedContent(
                        targetState = state.currentQuestion,
                        transitionSpec = {
                            slideInHorizontally(
                                animationSpec = tween(500)
                            ) { width -> width } with
                                    slideOutHorizontally(
                                        animationSpec = tween(500)
                                    ) { width -> -width } + fadeOut(animationSpec = tween(300))
                        },
                        label = "QuestionSlideHorizontal"
                    ) { currentQuestion ->
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = LocalAppColor.current.colorAnswerError.copy(
                                                alpha = 0.8f
                                            ),
                                            shape = RoundedCornerShape(AppDimension.default.dp6)
                                        )
                                ) {
                                    AppText(
                                        text = stringResource(R.string.quiz_find_meaning_title),
                                        modifier = Modifier
                                            .padding(
                                                vertical = AppDimension.default.dp4,
                                                horizontal = AppDimension.default.dp16
                                            ),
                                        style = AppTypography.default.body,
                                        color = LocalAppColor.current.colorHighLightRed
                                    )
                                }
                            }

                            item {
                                AppText(
                                    text = stringResource(R.string.quiz_sentence_nail_tag, state.currentQuestion?.question.orEmpty()),
                                    style = AppTypography.default.bodyExtraLargeBold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(
                                            top = AppDimension.default.dp32,
                                            start = AppDimension.default.dp16,
                                            end = AppDimension.default.dp16,
                                            bottom = AppDimension.default.dp24
                                        ),
                                    textAlign = TextAlign.Center
                                )
                            }

                            currentQuestion?.let {
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
                                            viewModel.selectAnswer(
                                                item.label.orEmpty(),
                                                item.isCorrect
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}