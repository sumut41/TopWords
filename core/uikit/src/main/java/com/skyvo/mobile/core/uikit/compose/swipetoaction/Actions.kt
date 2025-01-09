package com.skyvo.mobile.core.uikit.compose.swipetoaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.compose.text.AppText

@Composable
fun DeleteAction(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = LocalAppColor.current.colorBorderInputsDefault),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppText(
                text = "Sil",
                style = AppTypography.default.body,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DeleteAllAction(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = LocalAppColor.current.colorBorderInputsDefault),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppText(
                text = "Tümünü Sil",
                style = AppTypography.default.body,
                color = LocalAppColor.current.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FavouriteAction(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = LocalAppColor.current.colorBorderInputsDefault),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppText(
                text = "Favorilere Ekle",
                color = LocalAppColor.current.primary,
                style = AppTypography.default.body,
                textAlign = TextAlign.Center
            )
        }
    }
}