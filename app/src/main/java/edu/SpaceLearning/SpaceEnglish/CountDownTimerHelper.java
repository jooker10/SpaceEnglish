package edu.SpaceLearning.SpaceEnglish;

import android.os.CountDownTimer;

public class CountDownTimerHelper {

    private CountDownTimer countDownTimer;
    private OnCountdownListener listener;

    public CountDownTimerHelper(long millisInFuture, long countDownInterval) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (listener != null) {
                    listener.onTick((int) (millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onFinish();
                }
            }
        };
    }

    public void setListener(OnCountdownListener listener) {
        this.listener = listener;
    }

    public void start() {
        countDownTimer.start();
    }

    public void pause() {
        countDownTimer.cancel();
    }

    public void stop() {
        countDownTimer.cancel();
    }

    public void restart() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

    public interface OnCountdownListener {
        void onTick(int secondsUntilFinished);

        void onFinish();
    }
}
