package com.skyvo.mobile.top.words.feature.books.detail

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.manager.AppBookClickWordItem
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.book.BookRepository
import com.skyvo.mobile.core.shared.extension.convertJsonToList
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : BaseComposeViewModel<BookDetailUIState>() {
    override fun setInitialState(): BookDetailUIState {
        return BookDetailUIState()
    }

    override fun fetchExtras(extra: Bundle) {
        super.fetchExtras(extra)
        with(BookDetailFragmentArgs.fromBundle(extra)) {
            getBookDetail(bookId)
        }
    }

    private fun getBookDetail(id: Long?) {
        viewModelScope.launch {
            bookRepository.getBookDetail(id ?: 0).collect {
                it?.let { item ->
                    setState {
                        copy(
                            book = AppBook(
                                content = item.content,
                                contentTranslate = item.contentTr,
                                title = item.title,
                                imageUrl = item.imageUrl,
                                isNew = item.isNew,
                                level = item.level,
                                genre = item.genre,
                                min = item.min,
                                words = item.words.convertJsonToList<AppBookClickWordItem>()
                            )
                        )
                    }
                    updateSentences()
                }
            }
        }
    }

    fun updateSelectedWord(word: KeyValue?) {
        setState {
            copy(
                selectedWord = word
            )
        }
    }

    fun showTranslatedText() {
        setState {
            copy(
                isOriginalText = isOriginalText.not()
            )
        }
    }

    private fun updateSentences() {
        setState {
            val sentences = book?.content?.let {
                it.split(".").mapNotNull { s ->
                    s.trim().takeIf { it.isNotEmpty() }?.plus(".")
                }
            } ?: emptyList()

            val translations = book?.contentTranslate?.let {
                it.split(".").mapNotNull { s ->
                    s.trim().takeIf { it.isNotEmpty() }?.plus(".")
                }
            } ?: emptyList()

            copy(
                sentences = sentences,
                translations = translations
            )
        }
    }


    fun updateSelectedSentence(sentence: String) {
        setState {
            val translation = book?.let { book ->
                val sentences = book.content.orEmpty().split(".").mapNotNull { s ->
                    s.trim().takeIf { it.isNotEmpty() }?.plus(".")
                }
                val translations = book.contentTranslate.orEmpty().split(".").mapNotNull { s ->
                    s.trim().takeIf { it.isNotEmpty() }?.plus(".")
                }
                val index = sentences.indexOf(sentence)
                if (index != -1) translations.getOrNull(index) else null
            }
            copy(
                selectedSentence = sentence to translation,
            )
        }
    }
}