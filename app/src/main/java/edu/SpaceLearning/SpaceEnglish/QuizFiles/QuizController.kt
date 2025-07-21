package edu.SpaceLearning.SpaceEnglish.QuizFiles

import android.app.Activity
import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper
import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper.OnCountdownListener
import edu.SpaceLearning.SpaceEnglish.Listeners.ActivityFragmentInteractionListener
import edu.SpaceLearning.SpaceEnglish.Listeners.UiControllerListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Question
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SoundManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity
import java.util.Collections
import java.util.Locale
import java.util.Random
import kotlin.math.min

class QuizController(val requiredActivity : Activity, val uiControllerListener
                : UiControllerListener, val categoryType : String, val selectedCategoryList: List<Category>)  {
    private val quizQuestions: MutableList<Question> = ArrayList()

    private var quizCallback: QuizCallback? = null
    private var quizTimer: QuizTimer? = null

    private var interactionListener: ActivityFragmentInteractionListener? = null
    //private var adsClickListener: AdsClickListener? = null
    private var soundManager: SoundManager? = null
    private lateinit var currentQuestion: Question
    private var currentQuestionIndex = 0
    var maxTimerSeconds = 15
    private val maxAllowedAddedPoints = 10
    private var correctAnswersCount = 0
    private var wrongAnswersCount = 0

    init {
        soundManager = SoundManager()
       // rbDefaultColorTxt = binding.QuizCategoryOption1.textColors
       // binding.tvQuestionLabel.text = setAppNativeLanguage(Utils.nativeLanguage) // Choose the app native Language

    }

    companion object {
         var hasUserAnswered = false
    }

     fun createQuizQuestions() : MutableList<Question> {
        val randomIndexesSet: MutableSet<Int> = HashSet()
        val random = Random()

        for (i in selectedCategoryList.indices) {
            randomIndexesSet.clear() // Clear the set for each new iteration

            while (randomIndexesSet.size < 2) {   // Generate 2 random indices different from i
                val randomIndex = random.nextInt(Utils.maxQuestionsPerQuiz)
                if (randomIndex != i) {
                    randomIndexesSet.add(randomIndex)
                }
            }
            randomIndexesSet.add(i)   // Add i to the set    -- now total indices = 3

            val randomIndexesList: List<Int> = ArrayList(randomIndexesSet)    // Convert set to list
            Collections.shuffle(randomIndexesList)
            // Now randomList contains 3 unique indices: i and two others
            // You can use these indices to select questions from currentElementsList
            val mainElementQuestion = getTranslatedCategoryName(Utils.nativeLanguage, i) // here change the Element language
            val rbOption1 = selectedCategoryList[randomIndexesList[0]].englishName
            val rbOption2 = selectedCategoryList[randomIndexesList[1]].englishName
            val rbOption3 = selectedCategoryList[randomIndexesList[2]].englishName
            val optionsList = listOf(rbOption1,rbOption2,rbOption3)
            val rightAnswer = selectedCategoryList[i].englishName

            quizQuestions.add(Question(mainElementQuestion,optionsList , rightAnswer)
            )
        }
         return quizQuestions
    }

     fun goToNextQuestion() {
         MainActivity.textToSpeechManager?.stop()
        if (currentQuestionIndex < Utils.maxQuestionsPerQuiz)
        {
            startTimer()

            hasUserAnswered = false

            uiControllerListener.resetUIForNextQuestion()

            currentQuestion = quizQuestions[currentQuestionIndex]
            currentQuestionIndex++

            uiControllerListener.updateUIWithQuestion(currentQuestion,currentQuestionIndex) //...

        }
        else {
            hasUserAnswered = true
           stopTimer()
            finishQuiz()
        }
    }

    fun checkAnswer() {

        val checkedRadioID = uiControllerListener.getCheckedOptionId()
        if (checkedRadioID != -1) {
            hasUserAnswered = true
            stopTimer()

           uiControllerListener.updateNextButtonText("Next")
            val radioSelected = uiControllerListener.getCheckedOptionRadioButton(checkedRadioID)
            val userAnswer = radioSelected.text.toString()

            if (userAnswer == currentQuestion.correctAnswer) {
                handleCorrectAnswer()
            } else {
                handleIncorrectAnswer()
            }

            uiControllerListener.changeRightWrongAnswerColors(currentQuestion.correctAnswer) //  right answer (green color) , wrong answers (red color).

            if (currentQuestionIndex == Utils.maxQuestionsPerQuiz) {
                uiControllerListener.updateNextButtonText("Finish")
                MainActivity.textToSpeechManager?.speak("Final Question!")
            }
        } else {
            handleNoSelection()
        }
    }

     fun finishQuiz() {
        //  here you can finish the quiz with dialog and set scores...etc
       /* if (Utils.switchSimpleToVideoAds) {
            adsClickListener?.onShowInterstitialAd()
        } else {
            adsClickListener?.onShowRewardedAd()
        }*/
       // Utils.switchSimpleToVideoAds = !Utils.switchSimpleToVideoAds

        applyFinalScoreResults(correctAnswersCount)
    }

     fun applyFinalScoreResults(userScore: Int) {
        var pointsAdded = 0
        var elementsAdded = 0
        var msg = ""

        if (userScore.toFloat() == Utils.maxQuestionsPerQuiz.toFloat()) {
            pointsAdded = 10
            elementsAdded = 3
            msg = "Perfection Achieved!"
        } else if (userScore.toFloat() >= 0.75f * Utils.maxQuestionsPerQuiz) {
            pointsAdded = 5
            elementsAdded = 2
            msg = "Excellent"
        } else if (userScore.toFloat() >= 0.5f * Utils.maxQuestionsPerQuiz) {
            pointsAdded = 4
            elementsAdded = 1
            msg = "Average"
        } else if (userScore.toFloat() >= 0.25f * Utils.maxQuestionsPerQuiz) {
            pointsAdded = 1
            msg = "Below Average"
        } else {
            msg = "Needs improvement"
        }

        updateCategoryScore(categoryType, pointsAdded, elementsAdded)
        interactionListener?.onScoresSubmittedToDialog(categoryType, pointsAdded, elementsAdded, correctAnswersCount, msg)
    }

     fun updateCategoryScore(categoryType: String, pointsAdded: Int, elementsAdded: Int) {
        when (categoryType) {
            Constants.VERB_NAME -> {
                Scores.verbScore += pointsAdded
                Utils.allowedVerbsNumber =
                    min(Utils.totalVerbsNumber, Utils.allowedVerbsNumber + elementsAdded)
                Scores.verbQuizCompleted++
                Scores.verbAdded += elementsAdded
                if (pointsAdded == maxAllowedAddedPoints) {
                    Scores.verbQuizCompletedCorrectly++
                }
            }

            Constants.SENTENCE_NAME -> {
                Scores.sentenceScore += pointsAdded
                Utils.allowedSentencesNumber =
                    min(Utils.totalSentencesNumber, Utils.allowedSentencesNumber + elementsAdded)
                Scores.sentenceQuizCompleted++
                Scores.sentenceAdded += elementsAdded
                if (pointsAdded == maxAllowedAddedPoints) Scores.sentenceQuizCompletedCorrectly++
            }

            Constants.PHRASAL_NAME -> {
                Scores.phrasalScore += pointsAdded
                Utils.allowedPhrasalsNumber =
                    min(Utils.totalPhrasalsNumber, Utils.allowedPhrasalsNumber + elementsAdded)
                Scores.phrasalQuizCompleted++
                Scores.phrasalAdded += elementsAdded
                if (pointsAdded == maxAllowedAddedPoints) Scores.phrasalQuizCompletedCorrectly++
            }

            Constants.NOUN_NAME -> {
                Scores.nounScore += pointsAdded
                Utils.allowedNounsNumber =
                    min(Utils.totalNounsNumber, Utils.allowedNounsNumber + elementsAdded)
                Scores.nounQuizCompleted++
                Scores.nounAdded += elementsAdded
                if (pointsAdded == maxAllowedAddedPoints) Scores.nounQuizCompletedCorrectly++
            }

            Constants.ADJ_NAME -> {
                Scores.adjScore += pointsAdded
                Utils.allowedAdjsNumber =
                    min(Utils.totalAdjsNumber, Utils.allowedAdjsNumber + elementsAdded)
                Scores.adjQuizCompleted++
                Scores.adjAdded += elementsAdded
                if (pointsAdded == maxAllowedAddedPoints) Scores.adjQuizCompletedCorrectly++
            }

            Constants.ADV_NAME -> {
                Scores.advScore += pointsAdded
                Utils.allowedAdvsNumber =
                    min(Utils.totalAdvsNumber, Utils.allowedAdvsNumber + elementsAdded)
                Scores.advQuizCompleted++
                Scores.advAdded += elementsAdded
                if (pointsAdded == maxAllowedAddedPoints) Scores.advQuizCompletedCorrectly++
            }

            Constants.IDIOM_NAME -> {
                Scores.idiomScore += pointsAdded
                Utils.allowedIdiomsNumber =
                    min(Utils.totalIdiomsNumber, Utils.allowedIdiomsNumber + elementsAdded)
                Scores.idiomQuizCompleted++
                Scores.idiomAdded += elementsAdded
                if (pointsAdded == maxAllowedAddedPoints) Scores.idiomQuizCompletedCorrectly++
            }
        }
    }

    private fun getTranslatedCategoryName(translatedCategoryName: String, currentIndex: Int): String {
        return when (translatedCategoryName) {
            Constants.LANGUAGE_NATIVE_ARABIC -> selectedCategoryList[currentIndex].arabicName

            Constants.LANGUAGE_NATIVE_SPANISH -> selectedCategoryList[currentIndex].spanishName

            else -> selectedCategoryList[currentIndex].frenchName
        }
    }

    private fun handleCorrectAnswer() {
        soundManager?.playSound(requiredActivity, R.raw.coins_sound1)
        correctAnswersCount++
        uiControllerListener.updateCorrectAnswerScore(correctAnswersCount)

        val random = Random()
        val randomIndex = random.nextInt(Utils.phrasesCorrectAnswers.size)
        val text = Utils.phrasesCorrectAnswers[randomIndex]
        speakText(text)
    }


    private fun handleIncorrectAnswer() {
        wrongAnswersCount++
        soundManager?.playSound(requiredActivity, R.raw.error_sound1)
        val randomIndex = Random().nextInt(Utils.phrasesIncorrectAnswers.size)
        val text = Utils.phrasesIncorrectAnswers[randomIndex]
        speakText(text)

        uiControllerListener.updateWrongAnswerScore(wrongAnswersCount)
    }

    private fun handleNoSelection() {
        hasUserAnswered = false
       // binding.fabShareQstFriend.isClickable= false

        //val text = getString(R.string.quiz_toast_text_no_answer_selected)
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager?.speak("no text selected")
        }
    }

    private fun handleTimeoutAnswer() {
        stopTimer()
      // adsClickListener?.onShowInterstitialAd()
       wrongAnswersCount++
       //binding.tvQuizUserWrongAnswerCounter.text = userWrongScore.toString()
        uiControllerListener.updateWrongAnswerScore(wrongAnswersCount)

        hasUserAnswered = true
       //binding.btnConfirmNextCategory.text = "Next"
        uiControllerListener.updateNextButtonText("Next")

       val text = "You don't choose any answer , please make sure to choose an answer next time "
       speakText(text)

       uiControllerListener.updateOptionsUiAfterAnswers(currentQuestion.correctAnswer)

       // the counter is reached the final Question then change the btn to finish
       if (currentQuestionIndex == Utils.maxQuestionsPerQuiz) {
            uiControllerListener.updateNextButtonText("Finish")
       }
   }


    private fun speakText(text: String) {
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager?.setLanguage(Locale.ENGLISH)
            MainActivity.textToSpeechManager?.speak(text)
        }
    }

    fun startTimer() {
        quizTimer = QuizTimer(listener = object : QuizController.TimerListener {
            override fun onTick(secondsRemaining: Int) {
                quizCallback?.onTimerTick(secondsRemaining)
            }

            override fun onFinish() {
                quizCallback?.onTimerFinished()
                handleTimeoutAnswer()
                //moveToNextQuestion()
            }
        })
        quizTimer?.start()
    }

    fun stopTimer() {
        quizTimer?.stop()
    }

    fun setQuizCallback(callback: QuizCallback) {
        this.quizCallback = callback
    }

    interface QuizCallback {
        fun onTimerTick(secondsRemaining: Int)
        fun onTimerFinished()
    }


    // ✅ كلاس داخلي للتعامل مع التايمر
    private inner class QuizTimer(
          totalTimeInMillis: Long = 15000, // 30 ثانية
        intervalInMillis: Long = 1000,   // كل ثانية
        private val listener: TimerListener
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

    interface TimerListener {
        fun onTick(secondsRemaining: Int)
        fun onFinish()
    }
}