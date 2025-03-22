package com.skyvo.mobile.top.words.feature.words.flashcard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.database.course.CourseWordMockRepository
import com.skyvo.mobile.core.database.word.WordMockRepository
import com.skyvo.mobile.core.shared.extension.Pronouncer
import com.skyvo.mobile.core.uikit.compose.bottomsheet.AppInformationBottomSheet
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.button.AppSecondaryLargeButton
import com.skyvo.mobile.core.uikit.compose.card.FlashcardStack
import com.skyvo.mobile.core.uikit.compose.header.AppTopLongHeader
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlashCardFragment: BaseComposeFragment<FlashCardViewModel>() {

    override val viewModel: FlashCardViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: FlashCardViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        var isNavigateRight by remember { mutableStateOf(false) }
        var isNavigateLeft by remember { mutableStateOf(false) }
        var isShowWarningDialog by remember { mutableStateOf(false) }
        val speaker = remember { Pronouncer(requireContext(), state.learnLanguageCode.orEmpty()) }

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopLongHeader(
                        title = stringResource(id = com.skyvo.mobile.core.resource.R.string.flash_card_title),
                        onBackClickListener = {
                            navigateBack()
                        }
                    )
                },
                bottomView = {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                start = AppDimension.default.dp16,
                                end = AppDimension.default.dp16,
                                bottom = AppDimension.default.dp16
                            )
                    ) {
                        AppSecondaryLargeButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    end = AppDimension.default.dp8
                                ),
                            text = stringResource(id = com.skyvo.mobile.core.resource.R.string.i_know)
                        ) {
                            isShowWarningDialog = true
                        }

                        AppPrimaryLargeButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    start = AppDimension.default.dp8
                                ),
                            text = stringResource(id = com.skyvo.mobile.core.resource.R.string.not_much)
                        ) {
                            isNavigateRight = true
                        }
                    }
                }
            ) {
                state.items?.let {
                    FlashcardStack(
                        items = it,
                        isNavigateRight = isNavigateRight,
                        isNavigateLeft = isNavigateLeft,
                        onSwipeRight = { item ->
                            // speaker.speak(item.word)
                            isNavigateRight = false
                            viewModel.markWordAsUnknown(item)
                        },
                        onSwipeLeft = { item ->
                            // speaker.speak(item.word)
                            isNavigateLeft = false
                            viewModel.markWordAsKnown(item)
                        },
                        onStackCompleted = {
                            viewModel.next()
                        },
                        onFavoriteClick = { itemId, isFavorite ->
                            viewModel.toggleFavorite(itemId, isFavorite)
                        },
                        onSpeakClick = { word ->
                            speaker.speak(word)
                        }
                    )
                }
            }

            if (isShowWarningDialog) {
                AppInformationBottomSheet(
                    title = stringResource(id = com.skyvo.mobile.core.resource.R.string.i_know),
                    message = stringResource(id = com.skyvo.mobile.core.resource.R.string.i_know_word_dialog_message),
                    onDismiss = {
                        isShowWarningDialog = false
                    },
                    secondaryButtonText = stringResource(id = com.skyvo.mobile.core.resource.R.string.no),
                    onSecondaryButtonClick = {
                        isShowWarningDialog = false
                    },
                    primaryButtonText = stringResource(id = com.skyvo.mobile.core.resource.R.string.yes),
                    onPrimaryButtonClick = {
                        isShowWarningDialog = false
                        isNavigateLeft = true
                    }
                )
            }
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        val vm = FlashCardViewModel(
            UserMockManager(),
            CourseWordMockRepository(),
            WordMockRepository()
        )
        ContentView(vm)
    }
}