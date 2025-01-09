package com.skyvo.mobile.core.base.deeplink

import androidx.navigation.NavController

interface DeeplinkManager {
    fun handleDeeplink(deeplink: String?)
    fun setNavController(navController: NavController)
    fun setDeeplink(deeplink: String?)
    fun checkNavigationDeeplink(): String?
    fun clear()
}