package com.skyvo.mobile.top.words.feature.words.sentence

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.resource.SoundEffect
import com.skyvo.mobile.core.shared.extension.Pronouncer
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
import com.skyvo.mobile.core.resource.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SentenceQuizFragment : BaseComposeFragment<SentenceQuizViewModel>() {

    override val viewModel: SentenceQuizViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun ContentView(viewModel: SentenceQuizViewModel) {

        val state by viewModel.state.collectAsStateWithLifecycle()

        if (state.nextCount == 1) {
            if (state.playSoundType == 0) {
                SoundEffect(requireContext()).playSuccess()
            } else {
                SoundEffect(requireContext()).playError()
            }
        }

        val speaker = remember { Pronouncer(requireContext()) }

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopHeader(title = "") {
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
                            text = stringResource(id = com.skyvo.mobile.core.resource.R.string.check_answer),
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
                                        text = stringResource(R.string.quiz_sentence_arrange_title),
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
                                    text = stringResource(
                                        R.string.quiz_sentence_nail_tag,
                                        currentQuestion?.questionTranslate.orEmpty()
                                    ),
                                    style = AppTypography.default.bodyExtraLargeBold,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(
                                            top = AppDimension.default.dp32,
                                            start = AppDimension.default.dp16,
                                            end = AppDimension.default.dp16,
                                            bottom = AppDimension.default.dp16
                                        ),
                                    textAlign = TextAlign.Center
                                )

                                AppText(
                                    text = currentQuestion?.question.orEmpty()
                                        .replace("....", "_____"),
                                    style = AppTypography.default.bodyExtraLarge,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = AppDimension.default.dp16,
                                            end = AppDimension.default.dp16,
                                            bottom = AppDimension.default.dp24
                                        ),
                                    color = LocalAppColor.current.colorTextSubtler,
                                    textAlign = TextAlign.Center
                                )
                            }

                            currentQuestion?.let {
                                items(it.answerList.orEmpty()) { item ->
                                    AppChooseItemComponent(
                                        modifier = Modifier.padding(
                                            horizontal = AppDimension.default.dp16,
                                            vertical = AppDimension.default.dp8
                                        ),
                                        backgroundColor = LocalAppColor.current.colorBackgroundSelected,
                                        text = item.label.orEmpty(),
                                        isSelected = state.selectAnswer == item.label
                                    ) {
                                        speaker.speak(item.label.orEmpty())
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
}