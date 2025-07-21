package edu.SpaceLearning.SpaceEnglish

import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper
import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper.OnCountdownListener

class ForCopies {

    private var quizCallback: QuizCallback? = null
    private var quizTimer: QuizTimer? = null

    // مثال: استدعِ هذه الدالة عند بدء سؤال جديد
    fun startTimer() {
        quizTimer = QuizTimer(listener = object : ForCopies.Listener {
            override fun onTick(secondsRemaining: Int) {
                quizCallback?.onTimerTick(secondsRemaining)
            }

            override fun onFinish() {
                quizCallback?.onTimerFinished()
                moveToNextQuestion()
            }
        })
        quizTimer?.start()
    }

    fun stopTimer() {
        quizTimer?.stop()
    }

    private fun moveToNextQuestion() {
        // ضع هنا منطق الانتقال للسؤال التالي
    }

    fun setCallback(callback: QuizCallback) {
        this.quizCallback = callback
    }

    interface QuizCallback {
        fun onTimerTick(secondsRemaining: Int)
        fun onTimerFinished()
    }

    // ✅ كلاس داخلي للتعامل مع التايمر
    private inner class QuizTimer(
        private val totalTimeInMillis: Long = 30000, // 30 ثانية
        private val intervalInMillis: Long = 1000,   // كل ثانية
        private val listener: Listener
    ) {

        private val timerHelper: CountDownTimerHelper =
            CountDownTimerHelper(totalTimeInMillis, intervalInMillis)

        init {
            timerHelper.setListener(object : OnCountdownListener {
                override fun onTick(secondsUntilFinished: Int) {
                    listener.onTick(secondsUntilFinished)
                }

                override fun onFinish() {
                    listener.onFinish()
                }
            })
        }

        fun start() {
            timerHelper.start()
        }

        fun stop() {
            timerHelper.stop()
        }


    }
    interface Listener {
        fun onTick(secondsRemaining: Int)
        fun onFinish()
    }
}