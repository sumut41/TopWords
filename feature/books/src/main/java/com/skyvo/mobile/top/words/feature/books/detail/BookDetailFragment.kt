package com.skyvo.mobile.top.words.feature.books.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.skyvo.mobile.core.uikit.compose.bottomsheet.AppBottomSheet
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
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
import com.skyvo.mobile.core.uikit.util.setTextColor

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
        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopHeader(
                        title = state.book?.title.orEmpty(),
                        titleStyle = AppTypography.default.bodyXXlargeSemiBold,
                        onBackClickListener = { navigateBack() })
                }
            ) {
                LazyColumn {
                    item {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = AppDimension.default.dp16)
                                .height(AppDimension.default.bookHeight),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(state.book?.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "Story Image"
                        )
                    }

                    item {
                        AppBookInfoBox(state = state)
                    }

                    item {
                        if (state.isOriginalText) {
                            AppClickableStory(
                                modifier = Modifier.padding(horizontal = AppDimension.default.dp16),
                                text = state.book?.content.orEmpty(),
                                textColor = setTextColor(state.book?.level),
                                words = state.book?.words?.map {
                                    KeyValue(key = it.key, value = it.value)
                                } ?: emptyList()
                            ) { word ->
                                viewModel.updateSelectedWord(word)
                                showSelectedWordBottomSheet = true
                            }
                        } else {
                            AppText(
                                modifier = Modifier.padding(horizontal = AppDimension.default.dp16),
                                text = state.book?.contentTr.orEmpty(),
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
        }
    }

    @Composable
    fun AppBookInfoBox(state: BookDetailUIState) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimension.default.dp16,
                    vertical = AppDimension.default.dp8
                )
                .background(
                    color = LocalAppColor.current.primary,
                    shape = RoundedCornerShape(AppDimension.default.dp4)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(all = AppDimension.default.dp8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(
                    modifier = Modifier
                        .size(AppDimension.default.dp32)
                        .padding(end = AppDimension.default.dp8),
                    imageVector = ImageVector.vectorResource(com.skyvo.mobile.core.uikit.R.drawable.ic_clock),
                    contentDescription = "Book Icon",
                    tint = LocalAppColor.current.colorSurfaceBase
                )

                AppText(
                    text = state.book?.min.orEmpty(),
                    style = AppTypography.default.bodyLarge,
                    color = LocalAppColor.current.colorSurfaceBase
                )
            }
            AppSpacer()

            AppIcon(
                modifier = Modifier
                    .padding(end = AppDimension.default.dp16)
                    .size(AppDimension.default.dp30)
                    .clickable { viewModel.showTranslatedText() },
                imageVector = ImageVector.vectorResource(com.skyvo.mobile.core.uikit.R.drawable.ic_translate),
                contentDescription = "Translate Icon",
                tint = LocalAppColor.current.colorSurfaceBase
            )
        }
    }
}