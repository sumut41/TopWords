package com.skyvo.mobile.core.uikit.compose.button

import android.os.SystemClock
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.skyvo.mobile.core.uikit.theme.AppDimension

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    debounceTime: Long = 600L,
    content: @Composable RowScope.() -> Unit
) {
    var lastTimeClicked by remember { mutableLongStateOf(0L) }

    Button(
        onClick = {
            val now = SystemClock.uptimeMillis()
            if (now - lastTimeClicked > debounceTime) {
                onClick.invoke()
            }
            lastTimeClicked = now
        },
        modifier = modifier.height(AppDimension.default.dp42),
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        enabled = enabled,
        content = content
    )
}