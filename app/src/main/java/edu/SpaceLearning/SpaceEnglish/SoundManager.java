package edu.SpaceLearning.SpaceEnglish;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

public class SoundManager {
    private static final String TAG = "SoundManager";
    private static final int MAX_STREAMS = 5;
    private SoundPool soundPool;

    public SoundManager(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
    }

    public void playSound(Context context, int soundResourceId) {
        try {
            int soundId = soundPool.load(context, soundResourceId, 1);
            soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
                if (status == 0) { // 0 means success
                    soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
                } else {
                    Log.e(TAG, "Error loading sound");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error playing sound: " + e.getMessage());
        }
    }

    public void release() {
        soundPool.release();
    }
}
