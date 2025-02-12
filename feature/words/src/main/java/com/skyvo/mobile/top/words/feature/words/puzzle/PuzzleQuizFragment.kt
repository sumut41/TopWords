package com.skyvo.mobile.top.words.feature.words.puzzle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import kotlinx.coroutines.delay

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
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }

        if (state.nextCount == 1) {
            if (state.playSoundType == 0) {
                SoundEffect(requireContext()).playSuccess()
            } else {
                SoundEffect(requireContext()).playError()
            }
        }

        LaunchedEffect(state.nextCount) {
            if (state.nextCount == 1) {
                keyboardController?.hide()
            } else {
                delay(150)
                keyboardController?.show()
                focusRequester.requestFocus()
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

                            AppText(
                                text = "(İngilizce anlamını yaz.)",
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

                            OutlinedTextField(
                                value = state.selectAnswer.orEmpty(),
                                onValueChange = { viewModel.updateAnswer(it) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = AppDimension.default.dp32,
                                        end = AppDimension.default.dp32,
                                        top = AppDimension.default.dp48
                                    )
                                    .focusRequester(focusRequester),
                                textStyle = AppTypography.default.bigLargeTitleBold.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    color = LocalAppColor.current.colorTextMain.copy(alpha = 0.65f)
                                ),
                                placeholder = {
                                    AppText(
                                        text = "Type your answer",
                                        style = AppTypography.default.bodyLarge,
                                        color = LocalAppColor.current.colorTextSubtler.copy(alpha = 0.7f),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    cursorColor = LocalAppColor.current.colorTextMain
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        viewModel.nextQuestion()
                                    }
                                ),
                                singleLine = true
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
                                text = "Check Answer",
                                enabled = state.selectAnswer.isNullOrEmpty().not()
                            ) {
                                viewModel.checkAnswer()
                            }
                        }

                        AppShowAnswerCard(
                            selectAnswer = state.selectAnswer?.lowercase(),
                            correctWord = state.currentQuestion?.word?.lowercase(),
                            visible = state.showAnswer
                        ) {
                            viewModel.nextQuestion()
                        }
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
        ContentView(vm)
    }
}