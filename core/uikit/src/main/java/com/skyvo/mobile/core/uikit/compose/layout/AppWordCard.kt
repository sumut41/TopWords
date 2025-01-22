package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppWordCard (
    modifier: Modifier = Modifier,
    stepType: AppWordStepType? = AppWordStepType.STUDY,
    title: String? = null,
    subTitle: String? = null,
    isActive: Boolean = false
) {
    val stepColor = when (stepType) {
        AppWordStepType.STUDY -> LocalAppColor.current.colorA1Level
        AppWordStepType.QUIZ -> LocalAppColor.current.primary
        AppWordStepType.HARD -> LocalAppColor.current.colorC1Level
        else -> LocalAppColor.current.colorC2Level
    }
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppDimension.default.dp16,
                vertical = AppDimension.default.dp4
            )
            .background(
                color = LocalAppColor.current.colorTabBackgroundColor,
                shape = RoundedCornerShape(AppDimension.default.dp10)
            )
            .alpha(if (isActive) 1f else 0.4f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = AppDimension.default.dp12)
                .size(
                    width = 36.dp,
                    height = 32.dp
                )
                .background(
                    color = stepColor,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            AppIcon(
                modifier = Modifier.size(AppDimension.default.dp24),
                imageVector = ImageVector.vectorResource(
                    when(stepType) {
                        AppWordStepType.QUIZ -> R.drawable.ic_dummble
                        AppWordStepType.STUDY -> R.drawable.ic_words
                        else -> R.drawable.ic_star
                    }
                ),
                tint = Color.White,
                contentDescription = title.orEmpty()
            )
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column (
                modifier = Modifier
                    .weight(8f)
            ) {
                AppText(
                    modifier = Modifier
                        .padding(
                            top = AppDimension.default.dp8,
                            start = AppDimension.default.dp16
                        ),
                    text = title.orEmpty(),
                    style = AppTypography.default.body
                )
                AppText(
                    modifier = Modifier
                        .padding(
                            start = AppDimension.default.dp16,
                            bottom = AppDimension.default.dp8
                        ),
                    text = subTitle.orEmpty(),
                    style = AppTypography.default.bodySmall,
                    color = LocalAppColor.current.colorTextSubtler
                )
            }

            if (isActive.not()) {
                AppIcon(
                    modifier = Modifier
                        .weight(1f)
                        .size(AppDimension.default.dp24)
                        .padding(end = AppDimension.default.dp16)
                        .alpha(0.4f),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_lock),
                    tint = LocalAppColor.current.colorTextSubtler,
                    contentDescription = title.orEmpty()
                )
            }
        }
    }
}

enum class AppWordStepType(type: Int) {
    STUDY(1),
    QUIZ(2),
    HARD(3)
}

@Preview(showBackground = true)
@Composable
fun AppWordCardPreview() {
    AppPrimaryTheme {
        AppWordCard(
            modifier = Modifier,
            stepType = AppWordStepType.HARD,
            title = "New word 5",
            subTitle = "Learn words"
        )
    }
}