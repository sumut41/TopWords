package com.skyvo.mobile.core.base.navigation

sealed class NavDeeplinkDestination(
    private val _deeplink: String
) {
    companion object {
        val scheme = "top-words://"
    }

    val deeplink
        get() = if (this is External) _deeplink else scheme.plus(_deeplink)

    val path
        get() = _deeplink

    data class External(val url: String) : NavDeeplinkDestination(url)

    data class Internal(val url: String): NavDeeplinkDestination(
        _deeplink = url
    )

    // argument varsa
    data class TestFragment(
        val pageId: Int? = null // argument
    ): NavDeeplinkDestination(
        _deeplink = "home/test?pageId=$pageId" // xml deeplink
    )

    data class ResultWord(
        val title: String
    ): NavDeeplinkDestination(
        _deeplink = "result-word?title=$title"
    )

    data object WordsDashboard: NavDeeplinkDestination(
        _deeplink = "words/dashboard"
    )
}