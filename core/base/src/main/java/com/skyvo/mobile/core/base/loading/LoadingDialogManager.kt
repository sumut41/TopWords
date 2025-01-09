package com.skyvo.mobile.core.base.loading

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.graphics.drawable.toDrawable
import com.skyvo.mobile.core.base.R

class LoadingDialogManager : ILoadingDialogManager {
    private var dialog: Dialog? = null
    private lateinit var context: Context
    private var layoutResId: Int = 0
    private var loadingCount: Int = 0

    override fun createLoadingDialog(context: Context, layoutResId: Int, isShowLoading: Boolean) {
        this.context = context
        this.layoutResId = layoutResId
        dialog = Dialog(context, R.style.LoadingDialogTheme)
        dialog?.let {
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setCancelable(false)
            it.setContentView(layoutResId)
            it.setCanceledOnTouchOutside(false)
            if (isShowLoading) {
                it.show()
            }
            initTimer()
        }
    }

    override fun showLoading() {
        if (dialog == null) {
            createLoadingDialog(context, layoutResId)
        }

        if (dialog?.isShowing != true) {
            dialog?.show()
        }
        loadingCount++
    }

    override fun hideLoading() {
        loadingCount--
        startTimer()
    }

    override fun removeOnAllLoading() {
        if (dialog != null) {
            dialog?.dismiss()
        }
        dialog = null
        loadingCount = 0
    }

    private fun goneLoading() {
        try {
            if (loadingCount < 1) {
                if (dialog != null) {
                    dialog?.dismiss()
                }
                dialog = null
                loadingCount = 0
            }
        } catch (ex: Exception) {
            dialog = null
            loadingCount = 0
        }
    }

    private var timer: CountDownTimer? = null

    private fun initTimer() {
        timer = object : CountDownTimer(200L, 10L) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                goneLoading()
            }
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer?.start()
    }
}