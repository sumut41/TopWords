package com.skyvo.mobile.core.uikit.compose.toggle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun Material3Switch(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    debounceTime: Long = 300L,
    onCheckedChange: (Boolean) -> Unit
) {
    var lastTimeChecked by remember { mutableLongStateOf(0L) }

    val thumbSize = 20.dp
    val switchHeight = 24.dp
    val switchWidth = 40.dp
    val offsetEnd = switchWidth - thumbSize

    val scope = rememberCoroutineScope()
    var isChecked by remember { mutableStateOf(checked) }

    LaunchedEffect(checked) {
        isChecked = checked
    }

    Row(
        modifier = modifier
            .size(width = switchWidth, height = switchHeight)
            .background(
                if (isChecked) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface,
                CircleShape
            )
            .clickable {
                val now = System.currentTimeMillis()
                if (now - lastTimeChecked > debounceTime) {
                    isChecked = !isChecked
                    onCheckedChange(isChecked)
                    lastTimeChecked = now
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val offsetPx = with(LocalDensity.current) { offsetEnd.toPx().roundToInt() }
        Box(
            modifier = Modifier
                .offset { IntOffset(if (isChecked) offsetPx else 0, 0) }
                .size(thumbSize)
                .background(Color.White, CircleShape)
        )
    }
}