package com.skyvo.mobile.top.words.feature.words.flashcard

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.FiDataManager
import com.skyvo.mobile.core.base.manager.Level
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.uikit.compose.card.FlashcardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    private val dataManager: FiDataManager,
    private val userManager: UserManager
): BaseComposeViewModel<FlashCardUIState>() {

    override fun setInitialState(): FlashCardUIState {
        return FlashCardUIState()
    }

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            val wordList = when (userManager.customerLevel?.type) {
                "A1", "A2" -> dataManager.getBeginnerWordList()
                "B1", "B2" -> dataManager.getIntermediateWordList()
                "C1", "C2" -> dataManager.getAdvancedWordList()
                else -> dataManager.getBeginnerWordList() // Varsayılan olarak beginner
            }

            wordList?.let { words ->
                // Listeyi karıştır ve ilk 10 kelimeyi al
                val randomWords = words.shuffled().take(10)
                
                // FlashcardItem listesine dönüştür
                val flashcardItems = randomWords.map { word ->
                    FlashcardItem(
                        word = word.word.orEmpty(),
                        translate = word.translate.orEmpty(),
                        sentence = word.quiz.orEmpty().replace("....", word.word.orEmpty().lowercase()),
                        sentenceTranslate = word.quizTranslate.orEmpty(),
                        level = userManager.customerLevel?.name.orEmpty()
                    )
                }

                setState { copy(isLoading = false, items = flashcardItems) }
            }
        }
    }

    fun markWordAsKnown(word: FlashcardItem) {
        // Bilinen kelimeyi işaretle
        viewModelScope.launch {
            // TODO: Kelimeyi bilinen kelimeler listesine ekle
        }
    }

    fun markWordAsUnknown(word: FlashcardItem) {
        // Bilinmeyen kelimeyi işaretle
        viewModelScope.launch {
            // TODO: Kelimeyi bilinmeyen kelimeler listesine ekle
        }
    }
}