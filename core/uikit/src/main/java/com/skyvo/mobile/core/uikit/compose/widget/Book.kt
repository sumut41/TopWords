package com.skyvo.mobile.core.uikit.compose.widget

data class Book(
    val contentEn: String? = null,
    val contentTr: String? = null,
    val words: List<KeyValue>? = null,
    val imageUrl: String? = null,
    val title: String? = null,
    val level: String? = null,
    val genre: String? = null,
)