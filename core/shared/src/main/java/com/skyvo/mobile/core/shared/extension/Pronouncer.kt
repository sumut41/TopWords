package com.skyvo.mobile.core.shared.extension

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
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

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                // Konuşma başladı
            }

            override fun onDone(utteranceId: String?) {
                // Konuşma tamamlandı
            }

            override fun onError(utteranceId: String?) {
                // Hata oluştu
            }
        })
    }

    private fun getLocaleFromCode(code: String): Locale {
        return when (code) {
            "en" -> Locale.ENGLISH
            "fr" -> Locale.FRENCH
            "de" -> Locale.GERMAN
            "it" -> Locale.ITALY
            "ja" -> Locale.JAPAN
            "zh" -> Locale.CHINESE
            "el" -> Locale("el", "GR")
            "az" -> Locale("az", "AZ")
            "ru" -> Locale("ru", "RU")
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