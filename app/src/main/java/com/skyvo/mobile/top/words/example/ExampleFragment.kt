package com.skyvo.mobile.top.words.example

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.uikit.compose.header.AppTopHeader
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExampleFragment: BaseComposeFragment<ExampleViewModel>() {

    override val viewModel: ExampleViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView()
        }
    }

    @Composable
    private fun ContentView() {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            AppScaffold (
                header = {
                    AppTopHeader(
                        title = "dsf",
                        onBackClickListener = {
                            navigateBack()
                        }
                    )
                }
            ) {
                LazyColumn {
                    item {
                        AppText(
                            modifier = Modifier,
                            text = state.message.orEmpty()
                        )
                    }
                }
            }
        }
    }
}