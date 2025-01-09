package com.skyvo.mobile.core.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.Observer
import com.skyvo.mobile.core.base.activity.BaseActivity
import com.skyvo.mobile.core.base.viewmodel.UIEffect
import com.skyvo.mobile.core.base.navigation.NavigationEffect
import com.skyvo.mobile.core.base.util.collectSafe
import com.skyvo.mobile.core.base.viewmodel.BaseViewModel
import com.skyvo.mobile.core.base.viewmodel.FetchExtras

abstract class BaseComposeFragment<VM : BaseViewModel> : CoreFragment(),
    FetchExtras {

    abstract val viewModel: VM

    override var displayBottomNavigationBarMenu = false

    abstract fun onComposeCreateView(composeView: ComposeView)

    private lateinit var composeView: ComposeView

    override fun fetchExtras(extra: Bundle) {
        viewModel.fetchExtras(extra)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let(::fetchExtras)
    }

    override fun onResume() {
        setBottomNavigationVisibility()
        super.onResume()
    }

    private fun setBottomNavigationVisibility() {
        val activity = activity ?: return
        if (activity is BaseActivity<*>) {
            activity.setBottomNavigationVisibility(displayBottomNavigationBarMenu)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).also {
            composeView = it
            composeView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            composeView.setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onComposeCreateView(composeView)
    }

    override fun observeListener() {
        super.observeListener()
        viewModel.loading.observe(this, Observer {
            onLoading(it)
        })

        viewModel.error.observe(this, Observer {
            showErrorMessage(
                type = it.type,
                title = it.title,
                message = it.message,
                buttonActionType = it.buttonActionType
            )
        })

        viewModel.exception.observe(this, Observer {
            handleException(it)
        })

        collectSafe(viewModel.effect, ::onEffect)

        viewModel.removeAllLoading.observe(this, Observer {
            removeAllLoading()
        })
    }

    protected open fun onEffect(effect: UIEffect) {
        when (effect) {
            is NavigationEffect -> onNavigationEffect(effect)
        }
    }
}