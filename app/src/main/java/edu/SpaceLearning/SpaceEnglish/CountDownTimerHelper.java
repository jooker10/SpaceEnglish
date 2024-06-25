package edu.SpaceLearning.SpaceEnglish;

import android.os.CountDownTimer;

/**
 * CountDownTimerHelper is a helper class that encapsulates a CountDownTimer functionality
 * with callback listeners for timer ticks and timer finish events.
 */
public class CountDownTimerHelper {

    private final CountDownTimer countDownTimer;
    private OnCountdownListener listener;

    /**
     * Constructor to initialize the CountDownTimer with given duration and interval.
     *
     * @param millisInFuture    The total time in milliseconds the countdown is to run.
     * @param countDownInterval The interval along the way to receive onTick callbacks.
     */
    public CountDownTimerHelper(long millisInFuture, long countDownInterval) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Notify listener about tick event
                if (listener != null) {
                    listener.onTick((int) (millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                // Notify listener about finish event
                if (listener != null) {
                    listener.onFinish();
                }
            }
        };
    }

    /**
     * Sets the listener to receive countdown events.
     *
     * @param listener The listener implementing OnCountdownListener interface.
     */
    public void setListener(OnCountdownListener listener) {
        this.listener = listener;
    }

    /**
     * Starts the countdown timer.
     */
    public void start() {
        countDownTimer.start();
    }

    /**
     * Stops the countdown timer.
     */
    public void stop() {
        countDownTimer.cancel();
    }

    /**
     * Interface definition for a callback to be invoked when countdown events occur.
     */
    public interface OnCountdownListener {

        /**
         * Callback fired on each tick of the countdown.
         *
         * @param secondsUntilFinished The number of seconds until the countdown finishes.
         */
        void onTick(int secondsUntilFinished);

        /**
         * Callback fired when the countdown finishes.
         */
        void onFinish();
    }
}
