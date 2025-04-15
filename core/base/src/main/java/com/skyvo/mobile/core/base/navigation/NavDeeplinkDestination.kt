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
        val isQuiz: Boolean = false
    ): NavDeeplinkDestination(
        _deeplink = "result-word?isQuiz=$isQuiz"
    )

    data object WordsDashboard: NavDeeplinkDestination(
        _deeplink = "words/dashboard"
    )

    data class WordMeaningQuiz(
        val isSingleQuiz: Boolean = false
    ): NavDeeplinkDestination (
        _deeplink = "words/quiz/word-meaning?isSingleQuiz=$isSingleQuiz"
    )
}