package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

/**
 * SoundManager class manages the playing of sound effects using SoundPool.
 * It initializes a SoundPool instance with specific audio attributes and plays sound effects.
 */
public class SoundManager {
    private static final String TAG = "SoundManager";
    private static final int MAX_STREAMS = 5;
    private final SoundPool soundPool;

    /**
     * Constructor initializes the SoundPool with specified audio attributes.
     * Uses AudioAttributes to configure sound playback settings.
     */
    public SoundManager() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        // Build SoundPool instance with defined maximum streams and audio attributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            // For API levels lower than Lollipop, use deprecated constructor
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
    }

    /**
     * Plays a sound effect given its resource ID.
     *
     * @param context         The context to load the sound from.
     * @param soundResourceId The resource ID of the sound effect to play.
     */
    public void playSound(Context context, int soundResourceId) {
        try {
            // Load the sound into SoundPool and get the sound ID
            int soundId = soundPool.load(context, soundResourceId, 1);

            // Set a listener to know when the sound has finished loading
            soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
                if (status == 0) { // 0 means success
                    // Play the sound with full volume, looping disabled, and normal playback rate
                    soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
                } else {
                    Log.e(TAG, "Error loading sound");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error playing sound: " + e.getMessage());
        }
    }

    /**
     * Releases the resources used by the SoundPool.
     * Should be called when the SoundManager is no longer needed.
     */
    public void release() {
        soundPool.release();
    }
}
