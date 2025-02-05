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
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.database.word.WordMockRepository
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

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopLongHeader(
                        title = "How well do you remember this word?",
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
                            text = "Not Much"
                        ) { 
                            isNavigateLeft = true
                        }

                        AppPrimaryLargeButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    start = AppDimension.default.dp8
                                ),
                            text = "I Know"
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
                            isNavigateRight = false
                            viewModel.markWordAsKnown(item)
                        },
                        onSwipeLeft = { item ->
                            isNavigateLeft = false
                            viewModel.markWordAsUnknown(item)
                        },
                        onStackCompleted = {
                            navigateBack()
                        },
                        onFavoriteClick = { itemId, isFavorite ->
                            viewModel.toggleFavorite(itemId, isFavorite)
                        }
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        val vm = FlashCardViewModel(
            UserMockManager(),
            WordMockRepository()
        )
        ContentView(vm)
    }
}