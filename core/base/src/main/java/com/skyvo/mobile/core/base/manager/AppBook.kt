package com.skyvo.mobile.core.base.manager

data class AppBookParentModel (
   val books: List<AppBook>? = null
)

data class AppBook (
    val title: String? = null,
    val content: String? = null,
    val contentTr: String? = null,
    val imageUrl: String? = null,
    val isNew: String? = null,
    val level: String? = null,
    val min: String? = null,
    val genre: String? = null,
    val words: List<AppBookClickWordItem>? = null
)

data class AppBookClickWordItem(
    val key: String? = null,
    val value: String? = null
)