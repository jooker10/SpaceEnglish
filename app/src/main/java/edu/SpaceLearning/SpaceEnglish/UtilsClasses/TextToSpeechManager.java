package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

/**
 * TextToSpeechManager class manages text-to-speech functionality using TextToSpeech engine.
 * It initializes TextToSpeech with an English language setting, and provides methods to speak,
 * stop, and shutdown the text-to-speech engine.
 */
public class TextToSpeechManager {

    private final TextToSpeech textToSpeech;

    /**
     * Constructor initializes the TextToSpeech engine with English locale.
     *
     * @param context  The context to initialize TextToSpeech.
     * @param listener OnInitListener to handle initialization events.
     */
    public TextToSpeechManager(Context context, TextToSpeech.OnInitListener listener) {
        textToSpeech = new TextToSpeech(context, listener);
        textToSpeech.setLanguage(Locale.ENGLISH);
    }

    /**
     * Speaks the provided text using TextToSpeech engine.
     *
     * @param text The text to be spoken.
     */
    public void speak(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    /**
     * Stops the current speech playback.
     */
    public void stop() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    /**
     * Shuts down the TextToSpeech engine and releases associated resources.
     * This should be called when TextToSpeech functionality is no longer needed.
     */
    public void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
    }
}
