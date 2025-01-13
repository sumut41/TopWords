package com.skyvo.mobile.top.words.language

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.UserMockManager
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.header.AppTopLongHeader
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.picker.AppChooseItemComponent
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseLanguageFragment: BaseComposeFragment<ChooseLanguageViewModel>() {

    override val viewModel: ChooseLanguageViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    private fun ContentView(viewModel: ChooseLanguageViewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopLongHeader(
                        title = "What language are you want to study?",
                        onBackClickListener = {
                            navigateBack()
                        }
                    )
                },
                bottomView = {
                    AppPrimaryLargeButton(
                        modifier = Modifier,
                        text = "Continue",
                        enabled = state.enableButton
                    ) {
                        // next
                    }
                }
            ) {
                LazyColumn {
                    item {
                        AppSpacer(
                            height = AppDimension.default.dp64
                        )
                    }

                    item {
                        AppChooseItemComponent(
                            modifier = Modifier.padding(all = AppDimension.default.dp16),
                            text = "English",
                            isSelected = state.selectLanguageCode == "en",
                            startContent = {
                                Image(
                                    modifier = Modifier.size(
                                        width = 24.dp,
                                        height = 16.dp
                                    ),
                                    imageVector = ImageVector.vectorResource(com.skyvo.mobile.core.uikit.R.drawable.ic_flag_en),
                                    contentDescription = "English"
                                )
                            },
                            onSelectListener = { isSelect ->
                                viewModel.updateSelectLanguage(if (isSelect) "en" else null)
                            }
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        val vm = ChooseLanguageViewModel(UserMockManager())
        ContentView(vm)
    }
}