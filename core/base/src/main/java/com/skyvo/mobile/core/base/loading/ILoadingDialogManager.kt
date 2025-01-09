package com.skyvo.mobile.core.base.loading

import android.content.Context

interface ILoadingDialogManager {

    fun createLoadingDialog(context: Context, layoutResId: Int, isShowLoading: Boolean = false)

    fun showLoading()

    fun hideLoading()

    fun removeOnAllLoading()
}