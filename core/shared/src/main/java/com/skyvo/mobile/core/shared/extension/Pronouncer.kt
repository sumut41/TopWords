package com.skyvo.mobile.core.shared.extension

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale

class Pronouncer(context: Context) {
    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val locale = Locale.ENGLISH
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

    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun shutdown() {
        tts?.shutdown()
    }
}