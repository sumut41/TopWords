package com.skyvo.mobile.core.uikit.compose.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.theme.AppDimension

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    header: @Composable (() -> Unit)? = null,
    bottomView: @Composable (() -> Unit)? = null,
    backgroundColor: Color = LocalAppColor.current.background,
    headerColor: Color = LocalAppColor.current.colorSurfaceBase,
    bottomBackgroundColor: Color = Color.Transparent,
    paddingValues: PaddingValues = PaddingValues(top = AppDimension.default.dp56),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            if (header != null) {
                Row(
                    modifier = Modifier
                        .background(color = headerColor)
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .fillMaxWidth()
                ) {
                    header()
                }
            }
        },
        bottomBar = {
            if (bottomView != null) {
                Row(
                    modifier = Modifier
                        .background(color = bottomBackgroundColor)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .fillMaxWidth()
                        .padding(
                            bottom = AppDimension.default.dp16,
                            start = AppDimension.default.dp16,
                            end = AppDimension.default.dp16
                        )
                ) {
                    bottomView()
                }
            }
        },
        containerColor = backgroundColor,
        content = { insetPadding ->
            if (header != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(paddingValues)
                ) {
                    content(insetPadding)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(paddingValues)
                ) {
                    content(insetPadding)
                }
            }
        }
    )
}