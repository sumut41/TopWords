package com.skyvo.mobile.core.base.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.skyvo.mobile.core.base.activity.BaseActivity
import com.skyvo.mobile.core.base.viewmodel.UIEffect
import com.skyvo.mobile.core.base.navigation.NavigationEffect
import com.skyvo.mobile.core.base.util.collectSafe
import com.skyvo.mobile.core.base.viewmodel.BaseViewModel
import com.skyvo.mobile.core.base.viewmodel.FetchExtras

abstract class BaseFragment<VM : BaseViewModel> : CoreFragment(), FetchExtras {
    abstract val viewModel: VM

    override var displayBottomNavigationBarMenu: Boolean = false

    override fun fetchExtras(extra: Bundle) {
        viewModel.fetchExtras(extra)
        setBottomNavigationVisibility()
    }

    private fun setBottomNavigationVisibility() {
        val activity = activity ?: return
        if (activity is BaseActivity<*>) {
            activity.setBottomNavigationVisibility(displayBottomNavigationBarMenu)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let(::fetchExtras)
    }

    override fun observeListener() {
        super.observeListener()
        viewModel.loading.observe(this, Observer {
            onLoading(it)
        })

        viewModel.error.observe(this, Observer {
            showErrorMessage(
                type = it.type,
                message = it.message,
                buttonActionType = it.buttonActionType
            )
        })

        viewModel.exception.observe(this, Observer {
            handleException(it)
        })

        collectSafe(viewModel.effect, ::onEffect)

        viewModel.removeAllLoading.observe(this, Observer {
            removeOnAllLoading()
        })
    }

    protected open fun onEffect(effect: UIEffect) {
        when (effect) {
            is NavigationEffect -> onNavigationEffect(effect)
        }
    }
}