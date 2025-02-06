package com.skyvo.mobile.top.words.feature.words.sentence

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.picker.AppChooseItemComponent
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SentenceQuizFragment: BaseComposeFragment<SentenceQuizViewModel>() {

    override val viewModel: SentenceQuizViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: SentenceQuizViewModel) {

        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            AppScaffold (
                header = {
                    AppTopHeader(title = "") {
                        navigateBack()
                    }
                },
                bottomView = {
                    AppPrimaryLargeButton(
                        text = if (state.nextCount == 1) "Continue" else "Okey"
                    ) {
                        viewModel.nextQuestion()
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
                                    backgroundColor = if (state.showAnswer && item.isCorrect == true) {
                                        LocalAppColor.current.colorSuccess
                                    } else if (state.showAnswer && state.selectAnswer == item.label) {
                                        LocalAppColor.current.colorError
                                    } else {
                                        LocalAppColor.current.colorBackgroundSelected
                                    },
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