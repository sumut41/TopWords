package com.skyvo.mobile.top.words.feature.menu.appinfo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsFragment : BaseComposeFragment<AboutUsViewModel>() {
    override val viewModel: AboutUsViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        return composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    fun ContentView(viewModel: AboutUsViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        AboutUsFragmentView(state)
    }

    @Composable
    fun AboutUsFragmentView(state: AboutUsUIState) {
        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopHeader(
                        title = ""
                    ) {
                        navigateBack()
                    }
                }
            ) {
                LazyColumn {
                    item {
                        AppText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = AppDimension.default.dp16,
                                    vertical = AppDimension.default.dp16
                                ),
                            text = "TopWord ile en popüler kelimeleri öğrenmeye hazır mısın ?",
                            textAlign = TextAlign.Center,
                            style = AppTypography.default.brandTitle,
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppDimension.default.dp16)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(AppDimension.default.dp10))
                                    .border(
                                        width = AppDimension.default.dp1,
                                        color = LocalAppColor.current.primary,
                                        shape = RoundedCornerShape(AppDimension.default.dp10)
                                    )
                                    .padding(AppDimension.default.dp16)
                            ) {
                                Column {
                                    AppText(
                                        modifier = Modifier.padding(
                                            bottom = AppDimension.default.dp8
                                        ),
                                        text = "Flash Card",
                                        style = AppTypography.default.bodyLargeNormal
                                    )
                                    AppText(
                                        modifier = Modifier.padding(vertical = AppDimension.default.dp4),
                                        text = "✔ En çok kullanılan kelimeleri öğren",
                                        style = AppTypography.default.bodyPrimary
                                    )
                                    AppText(
                                        modifier = Modifier.padding(vertical = AppDimension.default.dp4),
                                        text = "✔ Quiz ile kelimeleri pekiştir",
                                        style = AppTypography.default.bodyPrimary
                                    )
                                    AppText(
                                        modifier = Modifier.padding(vertical = AppDimension.default.dp4),
                                        text = "✔ Öğrendiğin kelimeleri favorilerine ekle",
                                        style = AppTypography.default.bodyPrimary
                                    )
                                }
                            }
                        }

                    }

                    item {
                        AppSpacer(height = AppDimension.default.dp20)
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppDimension.default.dp16)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(AppDimension.default.dp10))
                                    .border(
                                        width = AppDimension.default.dp1,
                                        color = LocalAppColor.current.primary,
                                        shape = RoundedCornerShape(AppDimension.default.dp10)
                                    )
                                    .padding(AppDimension.default.dp16)
                            ) {
                                Column {
                                    AppText(
                                        modifier = Modifier.padding(
                                            bottom = AppDimension.default.dp8
                                        ),
                                        text = "Book",
                                        style = AppTypography.default.bodyLargeNormal
                                    )
                                    AppText(
                                        modifier = Modifier.padding(vertical = AppDimension.default.dp4),
                                        text = "✔ Her seviyeye uygun metinlerle günlük okuma yap",
                                        style = AppTypography.default.bodyPrimary
                                    )
                                    AppText(
                                        modifier = Modifier.padding(vertical = AppDimension.default.dp4),
                                        text = "✔ Öğrendiğin kelimelerin kullanımını gör",
                                        style = AppTypography.default.bodyPrimary
                                    )
                                    AppText(
                                        modifier = Modifier.padding(vertical = AppDimension.default.dp4),
                                        text = "✔ Yeni kelimeler öğrenmeye hazır ol",
                                        style = AppTypography.default.bodyPrimary
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