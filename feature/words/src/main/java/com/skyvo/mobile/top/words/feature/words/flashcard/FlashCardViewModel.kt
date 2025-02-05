package com.skyvo.mobile.top.words.feature.words.flashcard

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.LevelType
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.word.WordEntity
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.core.uikit.compose.card.FlashcardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    private val userManager: UserManager,
    private val wordRepository: WordRepository
): BaseComposeViewModel<FlashCardUIState>() {

    override fun setInitialState(): FlashCardUIState {
        return FlashCardUIState()
    }

    init {
        loadWords()
    }

    private fun loadWords() {
        showLoading()
        when (userManager.customerLevel?.type) {
            "A1", "A2" -> getBeginnerWordList()
            "B1", "B2" -> getIntermediateWordList()
            "C1", "C2" -> getAdvancedWordList()
            else -> getBeginnerWordList()
        }
    }

    private fun getBeginnerWordList() {
        viewModelScope.launch {
            wordRepository.getStudyWordList(
                level = LevelType.BEGINNER.key,
                languageCode = userManager.learnLanguage?.code
            ).collect {  response ->
                shuffleWord(response)
            }
        }
    }

    private fun getIntermediateWordList() {
        viewModelScope.launch {
            wordRepository.getStudyWordList(
                level = LevelType.INTERMEDIATE.key,
                languageCode = userManager.learnLanguage?.code
            ).collect {  response ->
                shuffleWord(response)
            }
        }
    }

    private fun getAdvancedWordList() {
        viewModelScope.launch {
            wordRepository.getStudyWordList(
                level = LevelType.ADVANCED.key,
                languageCode = userManager.learnLanguage?.code
            ).collect {  response ->
                shuffleWord(response)
            }
        }
    }

    private fun shuffleWord(wordList: List<WordEntity>?) {
        wordList?.let { words ->
            val randomWords = words.shuffled().take(10)

            val flashcardItems = randomWords.map { word ->
                FlashcardItem(
                    id = word.id,
                    word = word.word.orEmpty(),
                    translate = word.translate.orEmpty(),
                    sentence = word.quiz.orEmpty().replace("....", word.word.orEmpty().lowercase()),
                    sentenceTranslate = word.quizTranslate.orEmpty(),
                    level = userManager.customerLevel?.name.orEmpty()
                )
            }

            setState { copy(items = flashcardItems) }
            removeAllLoading()
        }
    }

    fun markWordAsKnown(word: FlashcardItem) {
        viewModelScope.launch {
            wordRepository.markWordAsKnown(word.id, userManager.learnLanguage?.code)
        }
    }

    fun markWordAsUnknown(word: FlashcardItem) {
        viewModelScope.launch {
            wordRepository.markWordAsUnKnown(word.id, userManager.learnLanguage?.code)
        }
    }

    fun toggleFavorite(itemId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            wordRepository.markWordAsFavorite(isFavorite, itemId, userManager.learnLanguage?.code)
        }
    }
}