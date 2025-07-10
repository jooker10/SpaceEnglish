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
        textToSpeech.setLanguage(Locale.ENGLISH)
    }

    /**
     * Speaks the provided text using TextToSpeech engine.
     *
     * @param text The text to be spoken.
     */
    fun speak(text: String?) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    /**
     * Stops the current speech playback.
     */
    fun stop() {
        if (textToSpeech != null) {
            textToSpeech.stop()
        }
    }

    /**
     * Shuts down the TextToSpeech engine and releases associated resources.
     * This should be called when TextToSpeech functionality is no longer needed.
     */
    fun shutdown() {
        if (textToSpeech != null) {
            textToSpeech.shutdown()
        }
    }
}
