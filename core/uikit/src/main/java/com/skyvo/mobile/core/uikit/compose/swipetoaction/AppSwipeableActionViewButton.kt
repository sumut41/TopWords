package com.skyvo.mobile.core.uikit.compose.swipetoaction

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.skyvo.mobile.core.uikit.theme.AppDimension
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppSwipeableActionViewButton(
    onDeleteClick: () -> Unit,
    onDeleteAllClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val defaultActionSize = AppDimension.default.dp64
    val endActionSizePx = with(density) {
        if (onDeleteAllClick != null) {
            ((defaultActionSize) * 2).toPx()
        } else {
            ((defaultActionSize) * 1).toPx()
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            anchors = DraggableAnchors {
                DragAnchors.Center at 0f
                DragAnchors.End at endActionSizePx
            },
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { AppDimension.default.dp100.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    DraggableItem(state = state,
        endAction = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterEnd)
                    .offset {
                        IntOffset(
                            ((-state
                                .requireOffset()) + endActionSizePx)
                                .roundToInt(), 0
                        )
                    }
            ) {
                DeleteAction(modifier = Modifier
                    .clickable {
                        coroutineScope.launch {
                            onDeleteClick()
                            state.animateTo(DragAnchors.Center)
                        }
                    }
                    .width(defaultActionSize)
                    .fillMaxHeight())
                if (onDeleteAllClick != null) {
                    DeleteAllAction(modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                onDeleteAllClick()
                                state.animateTo(DragAnchors.Center)
                            }
                        }
                        .width(defaultActionSize)
                        .fillMaxHeight())
                }
            }
        }, content = { content() })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppSwipeableActionViewButton(
    onFavouriteClick: () -> Unit,
    content: @Composable () -> Unit
){
    val density = LocalDensity.current
    val actionButtonSize = AppDimension.default.dp64
    val startActionSizePx = with(density) { (actionButtonSize).toPx() }

    val coroutineScope = rememberCoroutineScope()
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            anchors = DraggableAnchors {
                DragAnchors.Start at -startActionSizePx
                DragAnchors.Center at 0f
            },
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { AppDimension.default.dp100.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    DraggableItem(state = state,
        startAction = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
            ) {
                FavouriteAction(modifier = Modifier
                    .clickable {
                        onFavouriteClick()
                    }
                    .offset {
                        IntOffset(
                            ((-state
                                .requireOffset()) - startActionSizePx)
                                .roundToInt(), 0
                        )
                    }
                    .width(actionButtonSize)
                    .fillMaxHeight())
            }
        }, content = { content() })
}