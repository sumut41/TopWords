package com.skyvo.mobile.core.uikit.compose.progressbar

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppCountDownCircleView(
    modifier: Modifier = Modifier,
    time: Long = 60L,
    radius: Int = 100,
    isLoop: Boolean = false,
    isStart: Boolean,
    onFinishListener: (Boolean) -> Unit
) {
    var timeRemaining by remember { mutableLongStateOf(time) }
    var timer by remember { mutableStateOf<CountDownTimer?>(null) }

    val startTimer = {
        timer?.cancel()
        onFinishListener(false)
        timer = object : CountDownTimer(timeRemaining * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished / 1000
            }

            override fun onFinish() {
                onFinishListener(true)
                if (isLoop) {
                    timeRemaining = time
                }
            }
        }.start()
    }

    val stopTimer = { forceReset: Boolean ->
        timer?.cancel()
        onFinishListener(false)
        if (forceReset) {
            timeRemaining = time
        }
    }

    LaunchedEffect(isStart) {
        if (isStart) {
            if (timeRemaining == 0L){
                timeRemaining = time
            }
            startTimer()
        } else {
            stopTimer(false)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            stopTimer(true)
        }
    }

    Box(
        modifier = modifier
            .background(Color.Transparent)
            .size(radius.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier
                .size(radius.dp)
                .rotate(0f),
            color = LocalAppColor.current.colorBorderInputsDefault,
            strokeWidth = 4.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor
        )

        CircularProgressIndicator(
            progress = { (time - timeRemaining) / time.toFloat() },
            modifier = Modifier
                .size(radius.dp)
                .rotate(0f),
            color = LocalAppColor.current.colorIconBrandBoldRed,
            strokeWidth = 4.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor
        )

        AppText(
            text = if (timeRemaining > 9) "$timeRemaining sn" else "0$timeRemaining sn",
            style = AppTypography.default.bodyXXlargeSemiBold,
            color = if (isStart || timeRemaining == 0L) LocalAppColor.current.colorIconBrandBoldRed else LocalAppColor.current.colorBorderInputsDefault
        )
    }
}

/*
CircularProgressIndicator(
            progress = (time - timeRemaining) / time.toFloat(),
            strokeWidth = 4.dp,
            color = LocalAppColor.current.colorIconBrandBoldRed,
            modifier = Modifier
                .size(radius.dp)
                .rotate(0f),

        )
 */