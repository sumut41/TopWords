package com.skyvo.mobile.core.base.navigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDirections
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import timber.log.Timber

fun Fragment.setFragmentResultListener(
    requestKey: String,
    listener: (requestKey: String, bundle: Bundle) -> Unit
) {
    activity?.supportFragmentManager?.setFragmentResultListener(
        requestKey,
        viewLifecycleOwner
    ) { resultKey, result ->
        listener(resultKey, result)
    }
}

fun Fragment.navigateBackWithResult(
    @IdRes destination: Int? = null,
    result: Bundle? = null,
    requestKey: String? = null
): Boolean {
    if (requestKey != null && result != null) {
        activity?.supportFragmentManager?.setFragmentResult(requestKey, result)
    }
    return if (destination != null) {
        findNavController().popBackStack(destination, false)
    } else {
        findNavController().popBackStack()
    }
}

fun Fragment.navigate(directions: NavDirections) {
    findNavController().apply {
        currentDestination?.getAction(directions.actionId)?.run {
            navigate(directions)
        }
    }
}

fun Fragment.navigate(
    navDeeplinkDestination: NavDeeplinkDestination,
    popUpTo: Boolean = false,
    @IdRes popUpToId: Int = -1,
    popUpToInclusive: Boolean = false,
    enterAnim: Int? = null,
    exitAnim: Int? = null
) {
    try {
        findNavController().deeplinkNavigate(
            navDeeplinkDestination,
            popUpTo,
            popUpToId,
            popUpToInclusive,
            enterAnim,
            exitAnim,
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.navigateDeeplink(
    navDeeplinkDestination: NavDeeplinkDestination,
    popUpTo: Boolean = false,
    @IdRes popUpToId: Int = -1,
    popUpToInclusive: Boolean = false,
    isDeleteBackState: Boolean = false
) {
    navigate(
        navDeeplinkDestination,
        popUpTo,
        popUpToId,
        popUpToInclusive
    )

    if (isDeleteBackState) {
        deletePopBackStackList(popUpToId)
    }
}

fun Fragment.deletePopBackStackList(@IdRes destinationId: Int) {
    findNavController().clearBackStack(destinationId)
}

fun Fragment.navigate(@IdRes destinationId: Int) {
    findNavController().navigate(destinationId)
}

fun Fragment.navigate(
    @IdRes destinationId: Int,
    @NavigationRes graphResIdRes: Int
) {
    try {
        findNavController().apply {
            setGraph(graphResIdRes)
            navigate(destinationId)
        }
    } catch (ex: Exception) {
        Timber.e(ex)
    }
}

fun Fragment.navigate(@IdRes destinationId: Int, builder: NavOptionsBuilder.() -> Unit) {
    findNavController().navigate(destinationId, null, navOptions(builder))
}

fun Fragment.navigate(@IdRes destinationId: Int, args: Bundle?) {
    findNavController().navigate(destinationId, args)
}

fun FragmentActivity.onBackPressed(callback: () -> Unit) {
    onBackPressed(callback)
}

fun Fragment.navigateBack(@IdRes destinationId: Int? = null) {
    if (destinationId != null) {
        findNavController().popBackStack(destinationId, false)
    } else {
        if (!findNavController().popBackStack()) {
            activity?.finish()
        }
    }
}

fun Fragment.onBackPressed(isEnabled: Boolean, callback: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(isEnabled) {
            override fun handleOnBackPressed() {
                callback()
            }
        }
    )
}

fun Fragment.onBackPressed(callback: () -> Unit) {
    onBackPressed(true, callback)
}

fun Fragment.exitApp() {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME).flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

fun Fragment.relaunchApp() {
    val pm = requireActivity().packageManager
    val intent = pm.getLaunchIntentForPackage(requireActivity().packageName)
        ?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        ?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent?.data = requireActivity().intent?.data
    intent?.let {
        requireActivity().startActivity(intent)
    }
}

fun Fragment.restart() {
    val pm = requireActivity().packageManager
    val intent = pm.getLaunchIntentForPackage(requireActivity().packageName)
    val cn = intent?.component
    val mainIntent = Intent.makeRestartActivityTask(cn)
    mainIntent.setPackage(requireActivity().packageName)
    requireActivity().startActivity(mainIntent)
    Runtime.getRuntime().exit(0)
}
