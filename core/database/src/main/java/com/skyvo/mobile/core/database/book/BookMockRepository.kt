package com.skyvo.mobile.core.database.book

class BookMockRepository: BookRepository(
    bookDao = BookDaoMock()
)