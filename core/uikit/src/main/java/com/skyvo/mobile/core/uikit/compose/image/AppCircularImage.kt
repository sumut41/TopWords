package com.skyvo.mobile.core.uikit.compose.image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.base64toBitmap

@Composable
fun AppCircularImage(
    modifier: Modifier = Modifier,
    imageData: Any? = null,
    base64Format: String? = null,
    imageSize: Dp = AppDimension.default.dp56,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    borderWidth: Dp = AppDimension.default.dp1,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .background(
                color = LocalAppColor.current.colorBackgroundDecorativeGreySubtler,
                shape = CircleShape
            )
            .border(
                brush = Brush.linearGradient(
                    listOf(
                        // buraya colorHeaderBorderStart - End renklerini ekle topheaderın çizgisi
                    )
                ),
                width = borderWidth,
                shape = CircleShape
            )
            .clickable {
                onClick?.invoke()
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    if (base64Format != null) {
                        base64toBitmap(base64Format)
                    } else {
                        imageData
                    }
                )
                .build(),
            error = painterResource(R.drawable.ic_android_black_24dp),
            placeholder = painterResource(R.drawable.ic_android_black_24dp),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
        )
    }
}