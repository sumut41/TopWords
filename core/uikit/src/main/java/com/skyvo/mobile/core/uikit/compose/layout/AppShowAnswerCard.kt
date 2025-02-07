package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.skyvo.mobile.core.uikit.compose.button.AppSecondaryLargeButton
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppShowAnswerCard(
    selectAnswer: String? = null,
    correctWord: String? = null,
    visible: Boolean = false,
    buttonOnClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { it } + fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = getAnswerBackgroundColor(selectAnswer, correctWord),
                    shape = RoundedCornerShape(AppDimension.default.dp10)
                )
        ) {

            AppText(
                text = if (selectAnswer == correctWord) {
                    "That’s right!"
                } else {
                    "Ups.. That’s not quite right"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = AppDimension.default.dp24,
                        bottom = AppDimension.default.dp4,
                        start = AppDimension.default.dp16,
                        end = AppDimension.default.dp16
                    ),
                textAlign = TextAlign.Start,
                style = AppTypography.default.bodyExtraLargeBold,
                color = getAnswerTextColor(selectAnswer, correctWord)
            )

            AppText(
                text = "Answer:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppDimension.default.dp16,
                        end = AppDimension.default.dp16,
                        bottom = AppDimension.default.dp12
                    ),
                textAlign = TextAlign.Start,
                style = AppTypography.default.bodyBold,
                color = getAnswerTextColor(selectAnswer, correctWord)
            )

            AppText(
                text = correctWord.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppDimension.default.dp16,
                        end = AppDimension.default.dp16,
                        bottom = AppDimension.default.dp24
                    ),
                textAlign = TextAlign.Center,
                style = AppTypography.default.bodyLarge,
                color = getAnswerTextColor(selectAnswer, correctWord)
            )


            AppSecondaryLargeButton(
                text = "Next Question",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = AppDimension.default.dp16),
                containerColor = getAnswerTextColor(selectAnswer, correctWord),
                contentColor = Color.White,
                borderStrokeColor = getAnswerTextColor(selectAnswer, correctWord)
            ) {
                buttonOnClick.invoke()
            }
        }
    }
}

@Composable
fun getAnswerBackgroundColor(selectAnswer: String? = null, correctWord: String? = null): Color {
    return if (selectAnswer == correctWord) {
        LocalAppColor.current.colorAnswerSuccess
    } else {
        LocalAppColor.current.colorAnswerError
    }
}

@Composable
fun getAnswerTextColor(selectAnswer: String? = null, correctWord: String? = null): Color {
    return if (selectAnswer == correctWord) {
        LocalAppColor.current.colorSuccess
    } else {
        LocalAppColor.current.colorError
    }
}