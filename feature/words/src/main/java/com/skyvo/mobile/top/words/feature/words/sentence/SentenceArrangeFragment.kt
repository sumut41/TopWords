package com.skyvo.mobile.top.words.feature.words.sentence

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.resource.SoundEffect
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.layout.AppShowAnswerCard
import com.skyvo.mobile.core.uikit.compose.progressbar.AppLinearProgressbar

@AndroidEntryPoint
class SentenceArrangeFragment : BaseComposeFragment<SentenceArrangeViewModel>() {

    override val viewModel: SentenceArrangeViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun ContentView(viewModel: SentenceArrangeViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()

        if (state.showAnswer) {
            if (state.userAnswer == state.correctAnswer) {
                SoundEffect(requireContext()).playSuccess()
            } else {
                SoundEffect(requireContext()).playError()
            }
        }

        AppPrimaryTheme {
            AppScaffold(
                header = {
                   Row(
                       modifier = Modifier.fillMaxWidth()
                           .padding(
                               top = AppDimension.default.dp16,
                               start = AppDimension.default.dp16,
                               end = AppDimension.default.dp16
                           ),
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       AppText(
                           modifier = Modifier.weight(1.4f),
                           text = "${state.questionIndex}/${state.questionSentences?.size}",
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
                },
                bottomView = {
                    AppShowAnswerCard(
                        selectAnswer = state.userAnswer,
                        correctWord = state.correctAnswer,
                        visible = state.showAnswer
                    ) {
                        viewModel.nextQuestion()
                    }

                    AppPrimaryLargeButton(
                        text = stringResource(id = com.skyvo.mobile.core.resource.R.string.check_answer),
                        enabled = state.isCompleted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = AppDimension.default.dp16)
                    ) {
                        viewModel.checkAnswer()
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = AppDimension.default.dp16),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    state.questionSentences?.getOrNull(state.questionIndex)?.let { s ->
                        AppText(
                            text = "${s.quizTranslate}",
                            style = AppTypography.default.bodyExtraLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = AppDimension.default.dp56,
                                    bottom = AppDimension.default.dp48
                                ),
                            color = LocalAppColor.current.colorTextMain,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Cümle ve boşluklar
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = AppDimension.default.dp20,
                                bottom = AppDimension.default.dp72
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center,
                        maxItemsInEachRow = 5
                    ) {
                        state.displayWords.forEachIndexed { index, word ->
                            if (word.isBlank()) {
                                // Boş kutu
                                val emptyBoxIndex = state.selectedIndices.indexOf(index)
                                val filledWord = state.filledWords.getOrNull(emptyBoxIndex)
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 4.dp)
                                        .height(40.dp)
                                        .width(
                                            if (filledWord != null) {
                                                if (((filledWord.length * 16).dp + 16.dp ) > 64.dp) {
                                                    (filledWord.length * 16).dp + 16.dp
                                                } else {
                                                    64.dp
                                                }
                                            } else {
                                                64.dp
                                            }
                                        )
                                        .background(
                                            color = state.filledWords.getOrNull(emptyBoxIndex)?.let { 
                                                LocalAppColor.current.primary 
                                            } ?: LocalAppColor.current.colorBackgroundSelected,
                                            shape = RoundedCornerShape(AppDimension.default.dp10)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = LocalAppColor.current.colorBorder,
                                            shape = RoundedCornerShape(AppDimension.default.dp10)
                                        )
                                        .clip(RoundedCornerShape(AppDimension.default.dp10))
                                        .clickable(
                                            enabled = state.filledWords.getOrNull(emptyBoxIndex) != null
                                        ) {
                                            state.filledWords.getOrNull(emptyBoxIndex)?.let {
                                                viewModel.onFilledWordClick(it, emptyBoxIndex)
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    state.filledWords.getOrNull(emptyBoxIndex)?.let { filledWord ->
                                        AppText(
                                            text = filledWord,
                                            style = AppTypography.default.bodyLarge,
                                            color = LocalAppColor.current.colorTextMain,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            } else {
                                // Normal kelime
                                Box(
                                    modifier = Modifier
                                        .height(48.dp)
                                        .padding(horizontal = 4.dp, vertical = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AppText(
                                        text = word,
                                        style = AppTypography.default.bodyExtraLargeBold,
                                        color = LocalAppColor.current.colorTextMain
                                    )
                                }
                            }
                        }
                    }

                    // Kelime listesi
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppDimension.default.dp16),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        maxItemsInEachRow = 4
                    ) {
                        state.wordList.sortedBy { state.wordToPositionMap[it] }.forEach { word ->
                            if (!state.selectedWords.contains(word)) {
                                Box(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .padding(horizontal = 4.dp)
                                        .width((word.length * 16).dp + 16.dp)
                                        .background(
                                            color = LocalAppColor.current.colorBackgroundSelected,
                                            shape = RoundedCornerShape(AppDimension.default.dp10)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = LocalAppColor.current.colorBorder,
                                            shape = RoundedCornerShape(AppDimension.default.dp10)
                                        )
                                        .clip(RoundedCornerShape(AppDimension.default.dp10))
                                        .clickable {
                                            viewModel.onWordClick(word)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    AppText(
                                        text = word,
                                        style = AppTypography.default.bodyLarge,
                                        color = LocalAppColor.current.colorTextMain,
                                        textAlign = TextAlign.Center
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