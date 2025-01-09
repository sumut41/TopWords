package com.skyvo.mobile.core.base.navigation

import androidx.annotation.IdRes
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.skyvo.mobile.core.base.R

fun buildDeeplink(destination: NavDeeplinkDestination) =
    NavDeepLinkRequest.Builder
        .fromUri(destination.deeplink.toUri())
        .build()

fun NavController.deeplinkNavigate(
    navDeeplinkDestination: NavDeeplinkDestination,
    popUpTo: Boolean = false,
    @IdRes popUpToId: Int = -1,
    popUpToInclusive: Boolean = false,
    enterAnim: Int? = null,
    exitAnim: Int? = null
) {
    val builder = NavOptions.Builder()
    val popUpToDestinationId = if (popUpToId != -1) popUpToId else R.id.splashFragment

    if (popUpTo) {
        builder.setPopUpTo(destinationId = popUpToDestinationId, inclusive = popUpToInclusive)
    }

    if (exitAnim != null && enterAnim != null) {
        builder.setEnterAnim(enterAnim)
        builder.setExitAnim(exitAnim)
    }

    navigate(
        buildDeeplink(navDeeplinkDestination),
        builder.build()
    )
}