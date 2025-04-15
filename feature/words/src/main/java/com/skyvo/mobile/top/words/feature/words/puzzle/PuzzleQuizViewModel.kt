package com.skyvo.mobile.top.words.feature.words.puzzle

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.course.CourseWordRepository
import com.skyvo.mobile.core.database.word.WordRepository
import com.skyvo.mobile.top.words.feature.words.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LetterCount(
    val letter: Char,
    var remainingCount: Int,
    val totalCount: Int
)

@HiltViewModel
class PuzzleQuizViewModel @Inject constructor(
    private val courseWordRepository: CourseWordRepository,
    private val wordRepository: WordRepository
) : BaseComposeViewModel<PuzzleQuizUIState>() {

    private var currentProgress: Float = 0.0f
    private var letterCounts: MutableMap<Char, LetterCount> = mutableMapOf()

    override fun setInitialState(): PuzzleQuizUIState {
        return PuzzleQuizUIState()
    }

    init {
        getCurrentCourse()
    }

    private fun initializeLetterCounts(word: String) {
        letterCounts.clear()
        
        // Önce harfleri karıştır
        val shuffledWord = word.toList()
            .shuffled() // İlk karıştırma
            .shuffled() // İkinci karıştırma
            .joinToString("")
            .reversed() // Ters çevir
            .toList()
            .shuffled() // Son bir karıştırma daha
            .distinct() // Tekrar eden harfleri teke indir
        
        // Her harf için toplam sayıyı hesapla
        word.forEach { char ->
            val count = word.count { it == char }
            if (!letterCounts.containsKey(char)) {
                letterCounts[char] = LetterCount(char, count, count)
            }
        }

        // Karıştırılmış sırada harfleri state'e ekle
        setState {
            copy(
                availableLetters = shuffledWord.mapNotNull { char -> 
                    letterCounts[char]
                }
            )
        }
    }

    fun getCurrentCourse() {
        viewModelScope.launch {
            showLoading()
            courseWordRepository.getCurrentCourse().collect {
                it?.let { course ->
                    setState {
                        copy(
                            courseId = course.id
                        )
                    }
                    currentProgress = course.progress
                    getWordList(wordIds = course.wordIds)
                }
            }
        }
    }

    private fun getWordList(wordIds: String?) {
        viewModelScope.launch {
            val questionList: ArrayList<PuzzleModel> = arrayListOf()
            val wordIdList = (wordIds?.split(","))
            wordIdList?.let { list ->
                list.shuffled().forEach { id ->
                    wordRepository.getStudyWord(id.toLong()).collect {
                        it?.let { word ->
                            questionList.add(
                                PuzzleModel(
                                    word = word.word.orEmpty(),
                                    translate = word.translate.orEmpty()
                                )
                            )
                        }
                    }
                }
                questionList.shuffled().shuffled()
                setState {
                    copy(
                        selectIndex = 0,
                        wordList = questionList,
                        buttonEnable = false,
                        currentQuestion = questionList.first()
                    )
                }
                state.value.currentQuestion?.word?.let { initializeLetterCounts(it) }
            }
            removeAllLoading()
        }
    }

    private fun correct() {
        setState {
            copy(
                correctCount = correctCount + 1,
                playSoundType = 0
            )
        }
    }

    private fun unCorrect() {
        setState {
            copy(
                playSoundType = 1
            )
        }
    }

    fun addLetter(letter: Char) {
        val currentWord = state.value.currentQuestion?.word.orEmpty()
        val currentAnswer = state.value.selectAnswer.orEmpty()
        
        letterCounts[letter]?.let { letterCount ->
            if (letterCount.remainingCount > 0 && currentAnswer.length < currentWord.length) {
                letterCount.remainingCount--
                setState {
                    copy(
                        selectAnswer = currentAnswer + letter,
                        buttonEnable = (currentAnswer + letter).length == currentWord.length,
                        availableLetters = letterCounts.values.toList()
                    )
                }
            }
        }
    }

    fun removeLetterAt(index: Int) {
        val currentAnswer = state.value.selectAnswer.orEmpty()
        if (index < currentAnswer.length) {
            val removedLetter = currentAnswer[index]
            letterCounts[removedLetter]?.let { letterCount ->
                letterCount.remainingCount++
                val newAnswer = currentAnswer.removeRange(index, index + 1)
                setState {
                    copy(
                        selectAnswer = newAnswer,
                        buttonEnable = newAnswer.length == state.value.currentQuestion?.word?.length,
                        availableLetters = letterCounts.values.toList()
                    )
                }
            }
        }
    }

    fun checkAnswer() {
        val currentWord = state.value.currentQuestion?.word.orEmpty()
        val currentAnswer = state.value.selectAnswer.orEmpty()

        if (currentWord.lowercase().trim() == currentAnswer.lowercase().trim()) {
            correct()
        } else {
            unCorrect()
        }

        nextQuestion()
    }

    fun nextQuestion() {
        viewModelScope.launch {
            delay(120)
            if (state.value.nextCount == 1) {
                setState {
                    copy(
                        showAnswer = false,
                        nextCount = 0,
                        selectIndex = selectIndex + 1,
                        selectAnswer = ""
                    )
                }
                delay(250)
                val index = state.value.selectIndex
                if (index >= (state.value.wordList?.size ?: 0)) {
                    next()
                } else {
                    val nextWord = state.value.wordList?.get(index)
                    setState {
                        copy(
                            showAnswer = false,
                            selectAnswer = "",
                            currentQuestion = nextWord
                        )
                    }
                    nextWord?.word?.let { initializeLetterCounts(it) }
                }
            } else {
                setState {
                    copy(
                        nextCount = 1
                    )
                }
                delay(150)
                setState {
                    copy(
                        showAnswer = true
                    )
                }
            }
        }
    }

    fun next(isBack: Boolean = false) {
        viewModelScope.launch {
            courseWordRepository.updateCourse(
                id = state.value.courseId ?: 0L,
                isStart = true,
                progress = if (state.value.correctCount >= ((state.value.wordList?.size ?: 1) - 1)) 1f else (if (currentProgress == 0.80f) 0.90f else currentProgress)
            )
            delay(200)
            if (isBack) {
                navigateBack()
            } else {
                navigate(
                    navDeepLink = NavDeeplinkDestination.ResultWord(),
                    popUpTo = true,
                    popUpToInclusive = false,
                    popUpToId = R.id.wordsDashboardFragment
                )
            }
        }
    }
}