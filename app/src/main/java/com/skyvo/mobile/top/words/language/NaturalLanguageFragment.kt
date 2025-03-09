package com.skyvo.mobile.top.words.language

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.base.navigation.navigateBack
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.header.AppTopLongHeader
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.picker.AppChooseItemComponent
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class NaturalLanguageFragment : BaseComposeFragment<NaturalLanguageViewModel>() {

    override val viewModel: NaturalLanguageViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = false

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    private fun forceConfiguration(languageCode: String) {
        try {
            val locale = Locale(languageCode)
            val config = requireContext().resources.configuration
            config.setLocale(locale)
            requireContext().resources.updateConfiguration(
                config,
                requireContext().resources.displayMetrics
            )
        } catch (ex: Exception) {
            recordException(ex)
        }
    }

    @Composable
    private fun ContentView(viewModel: NaturalLanguageViewModel) {
        createLanguageList(viewModel)
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            AppScaffold(
                header = {
                    AppTopLongHeader(
                        title = stringResource(com.skyvo.mobile.core.resource.R.string.choose_native_language_title),
                        onBackClickListener = {
                            navigateBack()
                        }
                    )
                },
                bottomView = {
                    AppPrimaryLargeButton(
                        text = stringResource(com.skyvo.mobile.core.resource.R.string.continue_button),
                        onClick = {
                            forceConfiguration(state.selectedLanguage?.code.orEmpty())
                            viewModel.next()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = state.selectedLanguage != null
                    )
                }
            ) {
                LazyColumn {
                    item {
                        AppSpacer(
                            height = AppDimension.default.dp48
                        )
                    }

                    items(state.languages) { language ->
                        AppChooseItemComponent(
                            modifier = Modifier.padding(
                                horizontal = AppDimension.default.dp16,
                                vertical = AppDimension.default.dp8
                            ),
                            text = language.name,
                            isSelected = state.selectedLanguage == language,
                            startContent = {
                                Image(
                                    modifier = Modifier.size(
                                        width = 24.dp,
                                        height = 16.dp
                                    ),
                                    imageVector = ImageVector.vectorResource(language.icon),
                                    contentDescription = language.name
                                )
                            },
                            onSelectListener = { viewModel.onLanguageSelected(language) }
                        )
                    }

                    item {
                        AppSpacer(
                            height = AppDimension.default.dp100
                        )
                    }
                }
            }
        }
    }

    private fun createLanguageList(vm: NaturalLanguageViewModel) {
        val list: ArrayList<Language> = arrayListOf()
        list.add(
            Language(
                code = "es",
                name = "Española",
                icon = R.drawable.ic_flag_es
            )
        )
        list.add(
            Language(
                code = "fr",
                name = "Français",
                icon = R.drawable.ic_flag_fr
            )
        )
        list.add(
            Language(
                code = "de",
                name = "Deutsch",
                icon = R.drawable.ic_flag_de
            )
        )
        list.add(
            Language(
                code = "it",
                name = "Italiano",
                icon = R.drawable.ic_flag_it
            )
        )
        list.add(
            Language(
                code = "pt",
                name = "Português",
                icon = R.drawable.ic_flag_pt
            )
        )
        list.add(
            Language(
                code = "tr",
                name = "Türkçe",
                icon = R.drawable.ic_flag_tr
            )
        )
        list.add(
            Language(
                code = "ru",
                name = "русский",
                icon = R.drawable.ic_flag_ru
            )
        )
        vm.setLanguageList(list)
    }
} 