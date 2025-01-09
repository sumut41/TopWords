package com.skyvo.mobile.core.base.deeplink

import androidx.navigation.NavController
import com.skyvo.mobile.core.base.R
import com.skyvo.mobile.core.shared.store.Store
import javax.inject.Inject

class DeeplinkManagerImpl @Inject constructor(): DeeplinkManager {

    private var deeplink: String? = null
    private var navController: NavController? = null

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun setDeeplink(deeplink: String?) {
        this.deeplink = deeplink
    }

    override fun checkNavigationDeeplink(): String? {
        return deeplink
    }

    override fun clear() {
        this.deeplink = null
    }

    override fun handleDeeplink(deeplink: String?) {
        navController?.let {
            this.deeplink = deeplink
            val isLoginComplete = Store.shared.getStringValue(KEY_IS_COMPLETE_LOGIN).toBoolean()
            if (isLoginComplete) {
                navController?.navigate(R.id.dashboardFragment)
            } else {
                navController?.navigate(R.id.loginFragment)
            }
        }
    }

    companion object {
        private const val KEY_IS_COMPLETE_LOGIN = "KEY_IS_COMPLETE_LOGIN"
    }
}