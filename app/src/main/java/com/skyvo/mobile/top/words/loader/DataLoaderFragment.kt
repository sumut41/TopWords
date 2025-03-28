package com.skyvo.mobile.top.words.loader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.resource.R
import com.skyvo.mobile.top.words.file.ReadJsonFile
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DataLoaderFragment: BaseComposeFragment<DataLoaderViewModel>() {

    override val viewModel: DataLoaderViewModel by viewModels()

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView()
        }
        readFileJson()
    }

    private fun readFileJson() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val state = viewModel.state.value
                val jsonReader = ReadJsonFile.getInstance(requireContext())

                withContext(Dispatchers.Main) {
                    when(state.nativeLanguageCode) {
                        "tr" -> forceConfiguration("tr")
                        "es" -> forceConfiguration("es")
                        "de" -> forceConfiguration("de")
                        "az" -> forceConfiguration("az")
                        "it" -> forceConfiguration("it")
                        "fr" -> forceConfiguration("fr")
                    }
                }

                val (beginner, intermediate, advanced) = when {
                    state.nativeLanguageCode == "tr" && state.learnLanguageCode == "en" -> {
                        Triple(
                            jsonReader.parseJson(R.raw.words_beginner_tr_en),
                            jsonReader.parseJson(R.raw.words_intermediate_tr_en),
                            jsonReader.parseJson(R.raw.words_advanced_tr_en)
                        )
                    }
                    state.nativeLanguageCode == "es" && state.learnLanguageCode == "en" -> {
                        Triple(
                            jsonReader.parseJson(R.raw.words_beginner_es_en),
                            jsonReader.parseJson(R.raw.words_intermediate_es_en),
                            jsonReader.parseJson(R.raw.words_advanced_es_en)
                        )
                    }
                    state.nativeLanguageCode == "it" && state.learnLanguageCode == "en" -> {
                        Triple(
                            jsonReader.parseJson(R.raw.words_beginner_it_en),
                            jsonReader.parseJson(R.raw.words_intermediate_it_en),
                            jsonReader.parseJson(R.raw.words_advanced_it_en)
                        )
                    }
                    state.nativeLanguageCode == "de" && state.learnLanguageCode == "en" -> {
                        Triple(
                            jsonReader.parseJson(R.raw.words_beginner_de_en),
                            jsonReader.parseJson(R.raw.words_intermediate_de_en),
                            jsonReader.parseJson(R.raw.words_advanced_de_en)
                        )
                    }
                    else -> Triple(null, null, null)
                }

                withContext(Dispatchers.Main) {
                    viewModel.setBeginnerWord(beginner?.wordList)
                    viewModel.setIntermediate(intermediate?.wordList)
                    viewModel.setAdvanced(advanced?.wordList)
                    viewModel.getBookData()
                }
            } catch (e: Exception) {
                recordException(e)
            }
        }
    }

    private suspend fun forceConfiguration(languageCode: String) {
        withContext(Dispatchers.Main) {
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
    }

    @Composable
    private fun ContentView() {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = LocalAppColor.current.background
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AnimatedPreloader(modifier = Modifier.size(200.dp))

                    AppText(
                        text = stringResource(R.string.data_loader_loading_title),
                        modifier = Modifier.padding(
                            top = AppDimension.default.dp24,
                            start = AppDimension.default.dp16,
                            end = AppDimension.default.dp16
                        ),
                        style = AppTypography.default.bodyExtraLargeBold
                    )

                    AppText(
                        text = stringResource(R.string.data_loader_description),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = AppDimension.default.dp8,
                                horizontal = AppDimension.default.dp16
                            ),
                        textAlign = TextAlign.Center,
                        style = AppTypography.default.bodyPrimary,
                        color = LocalAppColor.current.colorTextSubtler
                    )
                }
            }
        }
    }

    @Composable
    fun AnimatedPreloader(modifier: Modifier = Modifier) {
        val preloaderLottieComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                com.skyvo.mobile.core.uikit.R.raw.loading_lottie
            )
        )

        val preloaderProgress by animateLottieCompositionAsState(
            preloaderLottieComposition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )


        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = modifier
        )
    }

    @Composable
    @Preview
    private fun Preview() {
        ContentView()
    }
}