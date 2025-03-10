package com.skyvo.mobile.top.words.feature.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skyvo.mobile.core.base.fragment.BaseComposeFragment
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.base.util.InAppReviewManager
import com.skyvo.mobile.core.shared.extension.shareApp
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.menu.AppMenuCard
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDarkColors
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppLightColors
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseComposeFragment<MenuViewModel>() {

    override val viewModel: MenuViewModel by viewModels()
    override var displayBottomNavigationBarMenu: Boolean = true

    override fun onComposeCreateView(composeView: ComposeView) {
        composeView.setContent {
            ContentView(viewModel)
        }
    }

    @Composable
    fun ContentView(
        viewModel: MenuViewModel
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppPrimaryTheme(
            navigationBarColor = if (isSystemInDarkTheme().not()) {
                AppLightColors.colorBottomMenu
            } else {
                AppDarkColors.colorBottomMenu
            }
        ) {
            AppScaffold(
                bottomView = {
                    AppMenuCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = AppDimension.default.dp64
                            ),
                        title = stringResource(id = com.skyvo.mobile.core.resource.R.string.about_us_menu_card_title),
                        subTitle = stringResource(id = com.skyvo.mobile.core.resource.R.string.about_us_menu_card_label),
                        color = LocalAppColor.current.colorTextSubtler,
                        horizontalPaddingValues = AppDimension.default.dp0,
                        menuIcon = R.drawable.ic_info
                    ) {
                        navigate(MenuFragmentDirections.actionMenuFragmentToAboutUsFragment())
                    }
                }
            ) {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = AppDimension.default.dp8,
                                    horizontal = AppDimension.default.dp16
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            AppText(
                                text = stringResource(id = com.skyvo.mobile.core.resource.R.string.menu_fragment_title),
                                style = AppTypography.default.bodyExtraLargeBold
                            )

                            Box(
                                modifier = Modifier
                                    .padding(start = AppDimension.default.dp24)
                                    .size(AppDimension.default.dp32)
                                    .background(
                                        color = LocalAppColor.current.colorIconBackground,
                                        shape = RoundedCornerShape(AppDimension.default.dp10)
                                    )
                                    .clickable {
                                        viewModel.changeTheme()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                AppIcon(
                                    modifier = Modifier.size(AppDimension.default.dp20),
                                    imageVector = if (state.isDarkTheme) {
                                        ImageVector.vectorResource(R.drawable.ic_sunny)
                                    } else {
                                        ImageVector.vectorResource(R.drawable.ic_night)
                                    },
                                    tint = LocalAppColor.current.colorIcon,
                                    contentDescription = "Theme"
                                )
                            }
                        }
                    }

                    item {
                        AppMenuCard(
                            modifier = Modifier
                                .padding(vertical = AppDimension.default.dp4),
                            title = stringResource(id = com.skyvo.mobile.core.resource.R.string.learned_words_title),
                            subTitle = stringResource(id = com.skyvo.mobile.core.resource.R.string.learned_words_label),
                            color = LocalAppColor.current.colorA2Level,
                            menuIcon = R.drawable.ic_nav_words_filled
                        ) {
                            navigate(MenuFragmentDirections.actionMenuFragmentToLearnedWordFragment())
                        }

                        AppMenuCard(
                            modifier = Modifier
                                .padding(vertical = AppDimension.default.dp4),
                            title = stringResource(id = com.skyvo.mobile.core.resource.R.string.favourite_words_title),
                            subTitle = stringResource(id = com.skyvo.mobile.core.resource.R.string.favourite_words_label),
                            color = LocalAppColor.current.colorC2Level,
                            menuIcon = R.drawable.ic_heart_filled
                        ) {
                            navigate(MenuFragmentDirections.actionMenuFragmentToFavoriteWordFragment())
                        }
                    }

                    item {
                        AppSpacer(
                            height = AppDimension.default.dp36
                        )
                    }

                    item {
                        AppMenuCard(
                            modifier = Modifier
                                .padding(vertical = AppDimension.default.dp4),
                            title = stringResource(id = com.skyvo.mobile.core.resource.R.string.rate_us_title),
                            subTitle = stringResource(id = com.skyvo.mobile.core.resource.R.string.rate_us_label),
                            color = LocalAppColor.current.primary,
                            menuIcon = R.drawable.ic_star
                        ) {
                            InAppReviewManager.showFeedbackDialog(this@MenuFragment)
                        }
                    }

                    item {
                        AppMenuCard(
                            modifier = Modifier
                                .padding(vertical = AppDimension.default.dp4),
                            title = stringResource(id = com.skyvo.mobile.core.resource.R.string.share_title),
                            subTitle = stringResource(id = com.skyvo.mobile.core.resource.R.string.share_label),
                            color = LocalAppColor.current.colorC1Level,
                            menuIcon = R.drawable.ic_share
                        ) {
                            shareApp(requireContext())
                        }
                    }
                }
            }
        }
    }
}