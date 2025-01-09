package com.skyvo.mobile.core.uikit.compose.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.button.AppSecondaryLargeButton
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography

@Composable
fun AppInformationBottomSheet(
    title: String? = null,
    message: String? = null,
    messageTextAlign: TextAlign = TextAlign.Center,
    primaryButtonText: String? = null,
    onPrimaryButtonClick: (() -> Unit)? = null,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
    isSingleButton: Boolean = false,
    onDismiss: () -> Unit
) {
    AppBottomSheet(
        onDismiss = onDismiss,
        title = title
    ) {
        Column {
            message?.let {
                AppText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimension.default.dp16),
                    text = it,
                    style = AppTypography.default.body,
                    textAlign = messageTextAlign
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = AppDimension.default.dp24,
                        horizontal = AppDimension.default.dp12
                    ),
                horizontalArrangement = Arrangement.Center
            ) {
                if (onSecondaryButtonClick != null && !isSingleButton) {
                    secondaryButtonText?.let { text ->
                        AppSecondaryLargeButton(
                            text = text,
                            onClick = onSecondaryButtonClick,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = AppDimension.default.dp4)
                        )
                    }
                }

                if (onPrimaryButtonClick != null) {
                    primaryButtonText?.let { text ->
                        AppPrimaryLargeButton(
                            text = text,
                            onClick = onPrimaryButtonClick,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = AppDimension.default.dp4)
                        )
                    }
                }
            }
        }
    }
}