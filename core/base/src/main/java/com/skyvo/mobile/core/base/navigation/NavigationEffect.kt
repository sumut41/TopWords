package com.skyvo.mobile.core.base.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.navigation.NavDirections
import com.skyvo.mobile.core.base.viewmodel.UIEffect

sealed class NavigationEffect: UIEffect {
    data class Navigate(val destination: NavDirections): NavigationEffect()

    data class NavigateDeeplink(
        val navDeeplinkDestination: NavDeeplinkDestination,
        val popUpTo: Boolean = false,
        @IdRes val popUpToId: Int = -1,
        val popUpToInclusive: Boolean = false,
        val enterAnim: Int? = null,
        val exitAnim: Int? = null,
    ): NavigationEffect()

    data class NavigateWithGraph(
        @IdRes val destinationId: Int,
        @NavigationRes val graphResId: Int
    ): NavigationEffect()

    data class NavigateBack(
        @IdRes val destinationId: Int? = null,
        val result: Bundle? = null,
        val requestKey: String? = null,
        val enterAnim: Int? = null,
        val exitAnim: Int? = null,
    ): NavigationEffect()

    object RelaunchApp: NavigationEffect()

    object Restart: NavigationEffect()
}