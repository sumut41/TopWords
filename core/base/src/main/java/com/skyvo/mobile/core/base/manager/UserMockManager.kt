package com.skyvo.mobile.core.base.manager

class UserMockManager: UserManager {
    override var isDarkTheme: Boolean = true
    override var learnLanguage: Language? = Language(
        code = "en",
        name = "English",
        icon = -1
    )
    override var nativeLanguage: Language? = Language(
        code = "tr",
        name = "Turkish",
        icon = -1
    )

    override var customerLevel: Level? = Level(
        "A1",
        "Beginner"
    )

    override var goalMinute: Int? = 10

    override var isCompletedSetup: Boolean = false
}