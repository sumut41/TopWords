package com.skyvo.mobile.top.words.feature.words.sentence

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.LevelType
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.top.words.feature.words.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SentenceArrangeViewModel @Inject constructor(
    private val userManager: UserManager,
    private val wordRepository: WordRepository
) : BaseComposeViewModel<SentenceArrangeUIState>() {

    override fun setInitialState(): SentenceArrangeUIState {
        return SentenceArrangeUIState()
    }

    init {
        getLearnedWordList()
    }

    private fun getLearnedWordList() {
        viewModelScope.launch {
            wordRepository.getRandomLevelWordList(
                level = getLevel().orEmpty(),
                languageCode = userManager.learnLanguage?.code.orEmpty()
            ).collect {
                setState {
                    copy(
                        questionSentences = it?.shuffled()?.shuffled(),
                        questionIndex = 0
                    )
                }
                setupSentence()
            }
        }
    }

    private fun getLevel(): String? {
        return when(userManager.customerLevel?.type) {
            "A1", "A2" -> LevelType.BEGINNER.key
            "B1", "B2" -> LevelType.INTERMEDIATE.key
            "C1", "C2" -> LevelType.ADVANCED.key
            else -> LevelType.BEGINNER.key
        }
    }

    private fun setupSentence() {
        val currentQuestion = state.value.questionSentences?.getOrNull(state.value.questionIndex) ?: return
        
        // Quiz cümlesini oluştur
        val sentence = currentQuestion.quiz.orEmpty().replace("....", currentQuestion.word.orEmpty().lowercase())
        // Kelimeleri ayır ve noktalama işaretlerini temizle
        val allWords = sentence.split(" ").map { word ->
            word.trim('.', ',', '!', '?', ';', ':')
        }
        
        // Hedef kelimenin indeksini bul
        val targetWord = currentQuestion.word.orEmpty().lowercase()
        val targetWordIndex = allWords.indexOf(targetWord)
        
        // Hedef kelimeyi ve 1-2 rastgele kelimeyi seç
        val additionalWordsCount = Random.nextInt(1, 3)
        val otherIndices = allWords.indices
            .filter { it != targetWordIndex }
            .shuffled()
            .take(additionalWordsCount)
        
        val selectedIndices = (otherIndices + targetWordIndex).sorted()
        
        // Seçilen kelimeleri orijinal sırasında tut
        val correctWords = selectedIndices.map { allWords[it] }
        
        // Görüntülenecek kelimeleri hazırla, noktalama işaretlerini koru
        val displayWords = sentence.split(" ").mapIndexed { index, word ->
            if (index in selectedIndices) "" else word
        }

        setState {
            copy(
                originalSentence = sentence,
                displayWords = displayWords,
                wordList = correctWords.shuffled(),
                filledWords = MutableList(correctWords.size) { null },
                correctOrder = correctWords,
                selectedIndices = selectedIndices,
                selectedWords = emptySet(),
                wordToPositionMap = correctWords.mapIndexed { index, word -> 
                    word to index 
                }.toMap()
            )
        }
    }

    fun onWordClick(word: String) {
        val currentState = state.value
        val emptyIndex = currentState.filledWords.indexOfFirst { it == null }
        
        if (emptyIndex != -1) {
            val newFilledWords = currentState.filledWords.toMutableList()
            newFilledWords[emptyIndex] = word
            
            setState {
                copy(
                    filledWords = newFilledWords,
                    selectedWords = selectedWords + word,
                    isCompleted = !newFilledWords.contains(null)
                )
            }
        }
    }

    fun onFilledWordClick(word: String, index: Int) {
        val currentState = state.value
        val newFilledWords = currentState.filledWords.toMutableList()
        newFilledWords[index] = null
        
        setState {
            copy(
                filledWords = newFilledWords,
                selectedWords = selectedWords - word,
                isCompleted = false
            )
        }
    }

    fun nextQuestion() {
        setState {
            copy(
                questionIndex = questionIndex + 1,
                showAnswer = false
            )
        }

        // Eğer hala gösterilecek soru varsa yeni soruyu hazırla
        if (state.value.questionIndex < (state.value.questionSentences?.size ?: 0)) {
            setupSentence()
        } else {
            navigate(
                navDeepLink = NavDeeplinkDestination.ResultWord(
                    title = "Harikasın, bitirdin!",
                    isQuiz = true
                ),
                popUpTo = true,
                popUpToInclusive = false,
                popUpToId = R.id.wordsDashboardFragment
            )
        }
    }

    fun checkAnswer() {
        val currentState = state.value
        
        // Kullanıcının oluşturduğu cümleyi hazırla
        val userSentence = currentState.displayWords.toMutableList()
        var filledWordIndex = 0
        currentState.selectedIndices.forEach { index ->
            userSentence[index] = currentState.filledWords[filledWordIndex++].orEmpty()
        }
        
        // Doğru cümleyi hazırla
        val correctSentence = currentState.displayWords.toMutableList()
        filledWordIndex = 0
        currentState.selectedIndices.forEach { index ->
            correctSentence[index] = currentState.correctOrder[filledWordIndex++]
        }

        setState {
            copy(
                userAnswer = userSentence.joinToString(" "),
                correctAnswer = correctSentence.joinToString(" "),
                showAnswer = true,
            )
        }
    }
} 