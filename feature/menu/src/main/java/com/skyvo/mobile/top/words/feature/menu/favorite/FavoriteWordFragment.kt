package com.skyvo.mobile.top.words.feature.menu.favorite

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.shared.extension.Pronouncer
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.picker.AppLearnedWordItemComponent
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteWordFragment: BaseComposeFragment<FavoriteWordViewModel>() {

    override val viewModel: FavoriteWordViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: FavoriteWordViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val speaker = remember { Pronouncer(context, state.learnLanguageCode.orEmpty()) }

        AppPrimaryTheme {
            AppScaffold (
                header = {
                    AppTopHeader(
                        title = stringResource(id = com.skyvo.mobile.core.resource.R.string.favourite_fragment_title)
                    ) {
                        navigateBack()
                    }
                }
            ) {
                LazyColumn {
                    state.items?.let {
                        items(it) { item ->
                            AppLearnedWordItemComponent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = AppDimension.default.dp16,
                                        vertical = AppDimension.default.dp8
                                    ),
                                word = item.word.orEmpty(),
                                translatedWord = item.translate.orEmpty(),
                                isFavorite = item.isFavorite,
                                onSpeakClick = { word ->
                                    speaker.speak(word)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}