package com.skyvo.mobile.core.base.manager

interface UserManager {
    var isDarkTheme: Boolean
    var learnLanguage: Language?
    var nativeLanguage: Language?
    var customerLevel: Level?
    var goalMinute: Int?
    var isCompletedSetup: Boolean
}