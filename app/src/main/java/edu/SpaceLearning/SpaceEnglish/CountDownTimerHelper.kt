package edu.SpaceLearning.SpaceEnglish

import android.os.CountDownTimer

/**
 * CountDownTimerHelper is a helper class that encapsulates a CountDownTimer functionality
 * with callback listeners for timer ticks and timer finish events.
 */
class CountDownTimerHelper(millisInFuture: Long, countDownInterval: Long) {
    private val countDownTimer: CountDownTimer
    private var listener: OnCountdownListener? = null

    /**
     * Constructor to initialize the CountDownTimer with given duration and interval.
     *
     * @param millisInFuture    The total time in milliseconds the countdown is to run.
     * @param countDownInterval The interval along the way to receive onTick callbacks.
     */
    init {
        countDownTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                // Notify listener about tick event
                if (listener != null) {
                    listener!!.onTick((millisUntilFinished / 1000).toInt())
                }
            }

            override fun onFinish() {
                // Notify listener about finish event
                if (listener != null) {
                    listener!!.onFinish()
                }
            }
        }
    }

    /**
     * Sets the listener to receive countdown events.
     *
     * @param listener The listener implementing OnCountdownListener interface.
     */
    fun setListener(listener: OnCountdownListener?) {
        this.listener = listener
    }

    /**
     * Starts the countdown timer.
     */
    fun start() {
        countDownTimer.start()
    }

    /**
     * Stops the countdown timer.
     */
    fun stop() {
        countDownTimer.cancel()
    }

    /**
     * Interface definition for a callback to be invoked when countdown events occur.
     */
    interface OnCountdownListener {
        /**
         * Callback fired on each tick of the countdown.
         *
         * @param secondsUntilFinished The number of seconds until the countdown finishes.
         */
        fun onTick(secondsUntilFinished: Int)

        /**
         * Callback fired when the countdown finishes.
         */
        fun onFinish()
    }
}
