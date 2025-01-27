package com.skyvo.mobile.core.uikit.compose.progressbar

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppCircleProgressbar(
    modifier: Modifier = Modifier,
    diameter: Dp = 100.dp,
    color: Color = LocalAppColor.current.primary,
    emptyColor: Color = LocalAppColor.current.colorCircleProgressDefault,
    width: Float = 20f,
    progress: Float = .75f
) {
    Box(
        content = {
            AppText(
                text = "${(progress * 100).toInt()}%",
                modifier = Modifier
                    .align(Alignment.Center),
                color = color,
                style = AppTypography.default.bodyBold
            )
            Canvas(
                modifier = modifier
                    .size(diameter)
                    .rotate(-90f)
                    .graphicsLayer {
                        rotationY = 360f
                    },
                onDraw = {
                    drawArc(
                        color = emptyColor,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        false,
                        style = Stroke(width = width)
                    )
                    drawArc(
                        color = color,
                        startAngle = 0f,
                        sweepAngle = progress * 360f,
                        false,
                        style = Stroke(
                            width = width,
                            cap = StrokeCap.Round
                        )
                    )
                }
            )
        }
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AppCircleProgressbarPreview() {
    AppPrimaryTheme {
        AppCircleProgressbar(
            modifier = Modifier
                .padding(16.dp),
            progress = 0.5f
        )
    }
}