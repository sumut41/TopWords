package com.skyvo.mobile.top.words.feature.books.model

import java.io.Serializable

data class BooksItem(
    val content: String? = null,
    val contentTr: String? = null,
    val words: List<KeyValueItem>? = null,
    val imageUrl: String? = null,
    val title: String? = null,
    val level: String? = null,
    val genre: String? = null,
    val isNew: Boolean? = null,
    val min: String? = null,
) : Serializable

data class KeyValueItem(
    val key: String? = null,
    val value: String? = null
) : Serializable
