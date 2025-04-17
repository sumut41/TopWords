package com.skyvo.mobile.top.words.feature.books.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.bottomsheet.AppBottomSheet
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppClickableStory
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.GetLevelIcon
import com.skyvo.mobile.core.uikit.util.setTextColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : BaseComposeFragment<BookDetailViewModel>() {
    override val viewModel: BookDetailViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = false

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    fun ContentView(
        viewModel: BookDetailViewModel
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        BookDetailView(state)
    }

    @Composable
    fun BookDetailView(
        state: BookDetailUIState
    ) {
        var showSelectedWordBottomSheet by remember { mutableStateOf(false) }
        var showSelectedSentenceBottomSheet by remember { mutableStateOf(false) }
        AppPrimaryTheme {
            AppScaffold (
                paddingValues = PaddingValues(top = AppDimension.default.dp0)
            ) {
                LazyColumn {
                    item {
                        Box {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(state.book?.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.FillWidth,
                                contentDescription = "Story Image"
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = AppDimension.default.dp48,
                                        start = AppDimension.default.dp16,
                                        end = AppDimension.default.dp16
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(AppDimension.default.dp32)
                                        .background(
                                            color = LocalAppColor.current.background,
                                            shape = RoundedCornerShape(AppDimension.default.dp10)
                                        )
                                        .clickable {
                                            navigateBack()
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    AppIcon(
                                        modifier = Modifier.size(AppDimension.default.dp20),
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                                        tint = LocalAppColor.current.colorIcon,
                                        contentDescription = "Theme"
                                    )
                                }

                                Row {

                                    Box(
                                        modifier = Modifier
                                            .size(AppDimension.default.dp32)
                                            .background(
                                                color = LocalAppColor.current.background,
                                                shape = RoundedCornerShape(AppDimension.default.dp10)
                                            )
                                            .border(
                                                width = AppDimension.default.dp1,
                                                color =  if (state.isOriginalText) LocalAppColor.current.background else LocalAppColor.current.primary,
                                                shape = RoundedCornerShape(AppDimension.default.dp10)
                                            )
                                            .clip(RoundedCornerShape(AppDimension.default.dp10))
                                            .clickable {
                                                viewModel.showTranslatedText()
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        AppIcon(
                                            modifier = Modifier.size(AppDimension.default.dp20),
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_translate),
                                            tint = LocalAppColor.current.colorIcon,
                                            contentDescription = "Translate"
                                        )
                                    }

                                    AppSpacer(
                                        width = AppDimension.default.dp8
                                    )

                                    state.book?.level?.let { level ->
                                        Row(
                                            modifier = Modifier
                                                .heightIn(min = AppDimension.default.dp32)
                                                .background(
                                                    color = LocalAppColor.current.background,
                                                    shape = RoundedCornerShape(AppDimension.default.dp10)
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                modifier = Modifier
                                                    .padding(
                                                        start = AppDimension.default.dp8,
                                                        end = AppDimension.default.dp4
                                                    )
                                                    .size(AppDimension.default.dp12),
                                                imageVector = ImageVector.vectorResource(
                                                    GetLevelIcon(level)
                                                ),
                                                contentDescription = level,
                                            )

                                            AppText(
                                                modifier = Modifier
                                                    .padding(
                                                        top = AppDimension.default.dp4,
                                                        bottom = AppDimension.default.dp4,
                                                        end = AppDimension.default.dp8
                                                    ),
                                                text = level,
                                                style = AppTypography.default.bodyBold,
                                                color = LocalAppColor.current.primary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        AppText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = AppDimension.default.dp16,
                                    end = AppDimension.default.dp16,
                                    top = AppDimension.default.dp16,
                                    bottom = AppDimension.default.dp8
                                ),
                            text = state.book?.title.orEmpty(),
                            style = AppTypography.default.headerBold
                        )

                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = AppDimension.default.dp16,
                                    end = AppDimension.default.dp16,
                                    bottom = AppDimension.default.dp16
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppText(
                                modifier = Modifier,
                                text = state.book?.genre.orEmpty(),
                                style = AppTypography.default.body,
                                color = LocalAppColor.current.colorTextSubtler
                            )

                            Row {
                                AppIcon(
                                    modifier = Modifier
                                        .padding(horizontal = AppDimension.default.dp8)
                                        .size(AppDimension.default.dp16),
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                                    tint = LocalAppColor.current.colorTextSubtler,
                                    contentDescription = "Time"
                                )

                                AppText(
                                    modifier = Modifier,
                                    text = state.book?.min.orEmpty(),
                                    style = AppTypography.default.body,
                                    color = LocalAppColor.current.colorTextSubtler
                                )
                            }
                        }
                    }

                    item {
                        if (state.isOriginalText) {
                            AppClickableStory(
                                modifier = Modifier.padding(horizontal = AppDimension.default.dp16),
                                text = state.book?.content.orEmpty(),
                                textColor = setTextColor(state.book?.level),
                                words = state.book?.words?.map {
                                    KeyValue(key = it.key, value = it.value)
                                } ?: emptyList(),
                                onSentenceClick = { sentence ->
                                    viewModel.updateSelectedSentence(sentence)
                                    showSelectedSentenceBottomSheet = true
                                },
                                sentences = state.sentences ?: emptyList(),
                                onWordClick = { word ->
                                    viewModel.updateSelectedWord(word)
                                    showSelectedWordBottomSheet = true
                                }
                            )
                        } else {
                            AppText(
                                modifier = Modifier.padding(horizontal = AppDimension.default.dp16),
                                text = state.book?.contentTranslate.orEmpty(),
                                style = AppTypography.default.bodyExtraLarge,
                            )
                        }
                    }
                }
            }
            if (showSelectedWordBottomSheet) {
                AppBottomSheet(
                    containerColor = setTextColor(state.book?.level),
                    contentColor = setTextColor(state.book?.level),
                    isWithTopPadding = false,
                    onDismiss = {
                        showSelectedWordBottomSheet = false
                        viewModel.updateSelectedWord(null)
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        AppText(
                            modifier = Modifier.padding(
                                start = AppDimension.default.dp24
                            ),
                            text = state.selectedWord?.key.orEmpty(),
                            style = AppTypography.default.bodyExtraLargeBold,
                            color = LocalAppColor.current.colorBooksLevel
                        )
                        AppText(
                            modifier = Modifier.padding(
                                start = AppDimension.default.dp24,
                                top = AppDimension.default.dp8,
                                bottom = AppDimension.default.dp16
                            ),
                            text = state.selectedWord?.value.orEmpty(),
                            style = AppTypography.default.bodyPrimary,
                            color = LocalAppColor.current.colorBooksLevel
                        )
                    }
                }
            }

            if (showSelectedSentenceBottomSheet) {
                AppBottomSheet(
                    containerColor = setTextColor(state.book?.level),
                    contentColor = setTextColor(state.book?.level),
                    isWithTopPadding = false,
                    onDismiss = {
                        showSelectedSentenceBottomSheet = false
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        AppText(
                            modifier = Modifier.padding(
                                start = AppDimension.default.dp24
                            ),
                            text = state.selectedSentence.first,
                            style = AppTypography.default.bodyExtraLargeBold,
                            color = LocalAppColor.current.colorBooksLevel
                        )
                        AppText(
                            modifier = Modifier.padding(
                                start = AppDimension.default.dp24,
                                top = AppDimension.default.dp8,
                                bottom = AppDimension.default.dp16
                            ),
                            text = state.selectedSentence.second.orEmpty(),
                            style = AppTypography.default.bodyPrimary,
                            color = LocalAppColor.current.colorBooksLevel
                        )
                    }
                }
            }
        }
    }
}