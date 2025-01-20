package com.skyvo.mobile.top.words.feature.books.detail

import android.os.Bundle
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import javax.inject.Inject

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
        }
    }

    fun updateSelectedWord(word: KeyValue?) {
        setState {
            copy(
                selectedWord = word
            )
        }
    }
}