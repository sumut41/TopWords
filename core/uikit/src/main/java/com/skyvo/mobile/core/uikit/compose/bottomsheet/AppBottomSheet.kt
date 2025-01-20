package com.skyvo.mobile.core.uikit.compose.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    title: String? = null,
    onDismiss: () -> Unit,
    isWithTopPadding: Boolean = true,
    isWithBottomPadding: Boolean = false,
    skipPartiallyExpanded: Boolean = false,
    isOutSideDismissDisable: Boolean = false,
    containerColor: Color = LocalAppColor.current.colorSurfaceBase,
    contentColor: Color = LocalAppColor.current.colorSurfaceBase,
    content: @Composable () -> Unit
) {

    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = {
            !(it == SheetValue.Hidden && isOutSideDismissDisable)
        }
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        shape = RoundedCornerShape(
            topStart = AppDimension.default.dp12,
            topEnd = AppDimension.default.dp12
        ),
        containerColor = containerColor,
        contentColor = contentColor
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
        ) {
            title?.let { text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = AppDimension.default.dp16,
                            end = AppDimension.default.dp16,
                            bottom = AppDimension.default.dp16,
                        ),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AppText(
                        text = text,
                        style = AppTypography.default.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppDimension.default.dp1)
                        .background(LocalAppColor.current.colorBorderInputsDefault)
                )
            }
            Box(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(top = if (isWithTopPadding) AppDimension.default.dp24 else AppDimension.default.dp0,
                       bottom = if (isWithBottomPadding) AppDimension.default.dp24 else AppDimension.default.dp0)
            ) {
                content()
            }
        }
    }
}