package edu.SpaceLearning.SpaceEnglish.QuizFiles

import android.app.Activity
import android.content.res.ColorStateList
import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
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

class QuizManager(val requiredActivity : Activity,val uiControllerListener : UiControllerListener, val categoryType : String, val currentSpecificCategorySubList: List<Category>)  {
      val questionsList: MutableList<Question> = ArrayList()
    private var timerHelper: CountDownTimerHelper? = null
    private var timerListener : TimerListener?  = null
    private var interactionListener: InteractionActivityFragmentsListener? = null
    private var adsClickListener: AdsClickListener? = null
    private var soundManager: SoundManager? = null
    private lateinit var currentQuestion: Question
    private var rbDefaultColorTxt: ColorStateList? = null
    private var currentQstIndex = 0
    var maxCounterTimer = 15
    private val maxAllowedAddedPoints = 10
    private var userRightScore = 0
    private var userWrongScore = 0

    init {
        soundManager = SoundManager()
       // rbDefaultColorTxt = binding.QuizCategoryOption1.textColors
       // binding.tvQuestionLabel.text = setAppNativeLanguage(Utils.nativeLanguage) // Choose the app native Language

    }

    companion object {
         var isAnswered = false
    }

     fun generateRequiredQuestionsList() : MutableList<Question> {
        val randomIndexesSet: MutableSet<Int> = HashSet()
        val random = Random()

        for (i in currentSpecificCategorySubList.indices) {
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
            val mainElementQuestion = setNativeMainElement(Utils.nativeLanguage, i) // here change the Element language
            val rbOption1 = currentSpecificCategorySubList[randomIndexesList[0]].engCategory
            val rbOption2 = currentSpecificCategorySubList[randomIndexesList[1]].engCategory
            val rbOption3 = currentSpecificCategorySubList[randomIndexesList[2]].engCategory
            val optionsList = listOf(rbOption1,rbOption2,rbOption3)
            val rightAnswer = currentSpecificCategorySubList[i].engCategory

            questionsList.add(Question(mainElementQuestion,optionsList , rightAnswer)
            )
        }
         return questionsList
    }

     fun moveToNextQuestion() {
        if (currentQstIndex < Utils.maxQuestionsPerQuiz)
        {
            startQuestionTimer(maxCounterTimer)

            isAnswered = false

            uiControllerListener.resetUIForNextQuestion()

            currentQuestion = questionsList[currentQstIndex]
            currentQstIndex++

            uiControllerListener.updateUIWithQuestion(currentQuestion,currentQstIndex)

        }
        else {
            isAnswered = true
           stopTimer()
            finishQuiz()
        }
    }

    fun checkAnswer() {

        val checkedRadioID = uiControllerListener.getCheckedOptionUiID()
        if (checkedRadioID != -1) {
            isAnswered = true
            stopTimer()

           uiControllerListener.updateNextButtonText("Next")
            val radioSelected = uiControllerListener.getCheckedOptionUi(checkedRadioID)
            val userAnswer = radioSelected.text.toString()

            if (userAnswer == currentQuestion.rightAnswer) {
                handleCorrectAnswer()
            } else {
                handleIncorrectAnswer()
            }

            uiControllerListener.changeRightWrongAnswerColors(currentQuestion.rightAnswer) //  right answer (green color) , wrong answers (red color).

            if (currentQstIndex == Utils.maxQuestionsPerQuiz) {
                uiControllerListener.updateNextButtonText("Finish")
                MainActivity.textToSpeechManager?.speak("Final Question!")
            }
        } else {
            handleNoAnswerSelected()
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

        finishQuizUpdateAll(userRightScore)
    }

     fun finishQuizUpdateAll(userScore: Int) {
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

        finishQuizUpdateScoresCategory(categoryType, pointsAdded, elementsAdded)
        interactionListener?.onSendScoresToDialog(categoryType, pointsAdded, elementsAdded, userRightScore, msg)
    }

     fun finishQuizUpdateScoresCategory(categoryType: String, pointsAdded: Int, elementsAdded: Int) {
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

    private fun setNativeMainElement(nativeLanguage: String, currentIndex: Int): String {
        return when (nativeLanguage) {
            Constants.LANGUAGE_NATIVE_ARABIC -> currentSpecificCategorySubList[currentIndex].arCategory

            Constants.LANGUAGE_NATIVE_SPANISH -> currentSpecificCategorySubList[currentIndex].spCategory

            else -> currentSpecificCategorySubList[currentIndex].frCategory
        }
    }

    private fun handleCorrectAnswer() {
        soundManager?.playSound(requiredActivity, R.raw.coins_sound1)
        userRightScore++
        uiControllerListener.updateUiRightScore(userRightScore)

        val random = Random()
        val randomIndex = random.nextInt(Utils.phrasesCorrectAnswers.size)
        val text = Utils.phrasesCorrectAnswers[randomIndex]
        speakEnglish(text)
    }


    private fun handleIncorrectAnswer() {
        userWrongScore++
        soundManager?.playSound(requiredActivity, R.raw.error_sound1)
        val randomIndex = Random().nextInt(Utils.phrasesIncorrectAnswers.size)
        val text = Utils.phrasesIncorrectAnswers[randomIndex]
        speakEnglish(text)

        uiControllerListener.updateUiWrongScore(userWrongScore)
    }

    private fun handleNoAnswerSelected() {
        isAnswered = false
       // binding.fabShareQstFriend.isClickable= false

        //val text = getString(R.string.quiz_toast_text_no_answer_selected)
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager?.speak("no text selected")
        }
    }

    private fun checkEmptyAnswerCounter() {
        stopTimer()
      // adsClickListener?.onShowInterstitialAd()
       userWrongScore++
       //binding.tvQuizUserWrongAnswerCounter.text = userWrongScore.toString()
        uiControllerListener.updateUiWrongScore(userWrongScore)

        isAnswered = true
       //binding.btnConfirmNextCategory.text = "Next"
        uiControllerListener.updateNextButtonText("Next")

       val text = "You don't choose any answer , please make sure to choose an answer next time "
       speakEnglish(text)

       uiControllerListener.updateOptionsUiAfterAnswers(currentQuestion.rightAnswer)

       // the counter is reached the final Question then change the btn to finish
       if (currentQstIndex == Utils.maxQuestionsPerQuiz) {
            uiControllerListener.updateNextButtonText("Finish")
       }
   }

    /*private fun changeRightWrongAnswerColors(rightAnswer: String) {
        for (i in 0..<binding.quizRadioGroup.childCount) {
            val radioButton = binding.quizRadioGroup.getChildAt(i) as RadioButton
            if (radioButton.text.toString() == rightAnswer) {
                radioButton.setTextColor(Color.GREEN)
            } else {
                radioButton.setTextColor(Color.RED)
            }
        }
    }*/

    private fun speakEnglish(text: String) {
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager?.setLanguage(Locale.ENGLISH)
            MainActivity.textToSpeechManager?.speak(text)
        }
    }

    fun setTimerListener(listener : TimerListener) {
        this.timerListener = listener
    }
    fun startQuestionTimer(seconds : Int) {
        timerHelper?.stop()
        timerHelper = CountDownTimerHelper(seconds * 1000L,1000L).apply {
            setListener(object : CountDownTimerHelper.OnCountdownListener
            {
                override fun onTick(secondsUntilFinished: Int) {
                    timerListener?.onTick(secondsUntilFinished)
                }

                override fun onFinish() {
                    timerListener?.onTimeFinished()
                    // update scores or ...
                    checkEmptyAnswerCounter()
                   // moveToNextQuestion()
                }

            })
            start()
        }
    }

    fun stopTimer() {
        timerHelper?.stop()
    }

    interface TimerListener {
        fun onTick(secondsLeft : Int)
        fun onTimeFinished()
    }
}