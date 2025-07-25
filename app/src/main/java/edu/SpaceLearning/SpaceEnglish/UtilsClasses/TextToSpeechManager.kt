package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import java.util.Locale

/**
 * TextToSpeechManager class manages text-to-speech functionality using TextToSpeech engine.
 * It initializes TextToSpeech with an English language setting, and provides methods to speak,
 * stop, and shutdown the text-to-speech engine.
 */
class TextToSpeechManager(context: Context?, listener: OnInitListener?) {
    private val textToSpeech = TextToSpeech(context, listener)

    /**
     * Constructor initializes the TextToSpeech engine with English locale.
     *
     * @param context  The context to initialize TextToSpeech.
     * @param listener OnInitListener to handle initialization events.
     */
    init {
        textToSpeech.language = Locale.ENGLISH
    }
    fun setLanguage(language : Locale) {
        textToSpeech.language = language


    }
    fun speak(text: String? = "no text selected") {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    /**
     * Stops the current speech playback.
     */
    fun stop() {
        textToSpeech.stop()
    }

    /**
     * Shuts down the TextToSpeech engine and releases associated resources.
     * This should be called when TextToSpeech functionality is no longer needed.
     */
    fun shutdown() {
        textToSpeech.shutdown()
    }
}
