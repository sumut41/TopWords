package com.skyvo.mobile.core.shared.extension

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class Pronouncer(context: Context, private val languageCode: String) {
    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val locale = getLocaleFromCode(languageCode)
                tts?.language = locale
            }
        }
    }

    private fun getLocaleFromCode(code: String): Locale {
        return when (code) {
            "en" -> Locale.ENGLISH
            "fr" -> Locale.FRENCH
            "de" -> Locale.GERMAN
            "es" -> Locale("es", "ES")
            "tr" -> Locale("tr", "TR")
            else -> Locale.ENGLISH
        }
    }

    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun shutdown() {
        tts?.shutdown()
    }
}