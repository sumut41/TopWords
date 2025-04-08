package com.skyvo.mobile.top.words.feature.words.puzzle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.database.course.CourseWordMockRepository
import com.skyvo.mobile.core.database.word.WordMockRepository
import com.skyvo.mobile.core.resource.SoundEffect
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.layout.AppShowAnswerCard
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.draw.clip
import com.skyvo.mobile.core.resource.R
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer

@AndroidEntryPoint
class PuzzleQuizFragment : BaseComposeFragment<PuzzleQuizViewModel>() {

    override val viewModel: PuzzleQuizViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: PuzzleQuizViewModel) {
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
                    AppTopHeader(title = "") {
                        viewModel.next(isBack = true)
                    }
                }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
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
                                    text = stringResource(R.string.meaning),
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

                        item("translate") {
                            AppText(
                                text = state.currentQuestion?.translate.orEmpty(),
                                style = AppTypography.default.wordBody,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = AppDimension.default.dp40,
                                        start = AppDimension.default.dp16,
                                        end = AppDimension.default.dp16,
                                        bottom = AppDimension.default.dp8
                                    ),
                                textAlign = TextAlign.Center
                            )

                            AppSpacer(height = AppDimension.default.dp56)

                            // Üstteki boş kutucuklar
                            EmptyLetterBoxes(
                                word = state.currentQuestion?.word.orEmpty(),
                                filledWord = state.selectAnswer.orEmpty()
                            )

                            AppSpacer(height = AppDimension.default.dp84)

                            // Alttaki karıştırılmış harfler
                            ShuffledLetterBoxes(
                                word = state.currentQuestion?.word.orEmpty(),
                                onLetterClick = { letter ->
                                    viewModel.addLetter(letter)
                                }
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .imePadding()
                            .padding(all = AppDimension.default.dp16)
                    ) {
                        if (state.nextCount != 1) {
                            AppPrimaryLargeButton(
                                text = stringResource(id = com.skyvo.mobile.core.resource.R.string.check_answer),
                                enabled = state.selectAnswer?.length == state.currentQuestion?.word?.length
                            ) {
                                viewModel.checkAnswer()
                            }
                        }

                        AppShowAnswerCard(
                            selectAnswer = state.selectAnswer?.lowercase()?.trim(),
                            correctWord = state.currentQuestion?.word?.lowercase()?.trim(),
                            visible = state.showAnswer
                        ) {
                            viewModel.nextQuestion()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun EmptyLetterBoxes(word: String, filledWord: String) {
        val spacing = 8.dp
        val maxBoxSize = 45.dp
        val horizontalPadding = AppDimension.default.dp16

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
        ) {
            val availableWidth = maxWidth - horizontalPadding * 2
            val boxSize =
                minOf(maxBoxSize, (availableWidth - (spacing * (word.length - 1))) / word.length)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                word.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(boxSize)
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
                            .clickable(
                                enabled = index < filledWord.length
                            ) {
                                viewModel.removeLetterAt(index)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        AppText(
                            text = if (index < filledWord.length) filledWord[index].toString()
                                .lowercase() else "",
                            style = AppTypography.default.bodyBold,
                            color = LocalAppColor.current.colorTextMain
                        )
                    }
                    if (index < word.length - 1) {
                        Spacer(modifier = Modifier.width(spacing))
                    }
                }
            }
        }
    }

    @Composable
    private fun ShuffledLetterBoxes(word: String, onLetterClick: (Char) -> Unit) {
        val spacing = 8.dp
        val maxBoxSize = 45.dp
        val horizontalPadding = AppDimension.default.dp16
        val state by viewModel.state.collectAsStateWithLifecycle()
        val availableLetters = state.availableLetters.filter { it.remainingCount > 0 }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
        ) {
            val availableWidth = maxWidth - horizontalPadding * 2
            val boxSize = minOf(
                maxBoxSize,
                (availableWidth - (spacing * (availableLetters.size - 1))) / availableLetters.size
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                availableLetters.forEach { letterCount ->
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()

                    Box(
                        modifier = Modifier
                            .size(boxSize)
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
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onLetterClick(letterCount.letter)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        AppText(
                            text = letterCount.letter.toString().lowercase(),
                            style = AppTypography.default.bodyLarge,
                            color = LocalAppColor.current.colorTextMain
                        )
                    }
                    if (letterCount != availableLetters.last()) {
                        Spacer(modifier = Modifier.width(spacing))
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        val vm = PuzzleQuizViewModel(
            CourseWordMockRepository(),
            WordMockRepository()
        )
        vm.getCurrentCourse()
        ContentView(vm)
    }
}