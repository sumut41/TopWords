package com.skyvo.mobile.core.database.word

class WordMockRepository: WordRepository(
    wordDao = WorkDaoMock()
)