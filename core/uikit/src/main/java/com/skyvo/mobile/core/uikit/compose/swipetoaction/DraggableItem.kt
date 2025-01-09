package com.skyvo.mobile.core.uikit.compose.swipetoaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.IntOffset
import com.skyvo.mobile.core.uikit.theme.AppDimension
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableItem(
    state: AnchoredDraggableState<DragAnchors>,
    content: @Composable BoxScope.() -> Unit,
    startAction: @Composable (BoxScope.() -> Unit)? = {},
    endAction: @Composable (BoxScope.() -> Unit)? = {},
) {
   Box(
       modifier = Modifier
           .fillMaxWidth()
           .clip(RectangleShape)
           .height(AppDimension.default.dp100)
   ) {
       endAction?.let {
           endAction()
       }
       startAction?.let {
           startAction()
       }
       Box(
           modifier = Modifier
               .fillMaxWidth()
               .align(Alignment.CenterStart)
               .offset {
                   IntOffset(
                       x = -state.requireOffset().roundToInt(),
                       y = 0
                   )
               }
               .anchoredDraggable(state, orientation = Orientation.Horizontal, reverseDirection = true),
           content = content
       )
   }
}