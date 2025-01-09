package com.skyvo.mobile.core.base.fragment

import android.view.WindowManager

enum class SoftKeyboardInputEnum(val value:Int) {
    ADJUST_RESIZE(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE),
    ADJUST_PAN(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN),
    STATE_VISIBLE(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE),
    STATE_ALWAYS_VISIBLE(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE),
}