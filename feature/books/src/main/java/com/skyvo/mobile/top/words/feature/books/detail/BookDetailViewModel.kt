package com.skyvo.mobile.top.words.feature.books.detail

import android.os.Bundle
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor() : BaseComposeViewModel<BookDetailUIState>() {
    override fun setInitialState(): BookDetailUIState {
        return BookDetailUIState()
    }

    override fun fetchExtras(extra: Bundle) {
        super.fetchExtras(extra)
        with(BookDetailFragmentArgs.fromBundle(extra)) {
            val args = this
            setState {
                copy(
                    book = args.book
                )
            }
            updateSentences()
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

            val translations = book?.contentTr?.let {
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
                val translations = book.contentTr.orEmpty().split(".").mapNotNull { s ->
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