/*
 * File: QuizCategoriesFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment for displaying and managing quiz questions within specified categories.
 */
package edu.SpaceLearning.SpaceEnglish.QuizFrags

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper
import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper.OnCountdownListener
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess.Companion.getInstance
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Question
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SoundManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity
import edu.SpaceLearning.SpaceEnglish.databinding.QuizCategoriesFragmentBinding
import java.util.Collections
import java.util.Random
import kotlin.math.min

class QuizCategoriesFragment : Fragment(), OnCountdownListener {
    private lateinit var binding: QuizCategoriesFragmentBinding
    private var interactionListener: InteractionActivityFragmentsListener? = null
    private var adsClickListener: AdsClickListener? = null
    private var soundManager: SoundManager? = null

    private var countDownTimerHelper: CountDownTimerHelper? = null
    private var maxCounterTimer = 15
    private val maxAllowedAddedPoints = 10

    private var categoryType: String = ""

    private var rbDefaultColorTxt: ColorStateList? = null

    private val questionsList: MutableList<Question> = ArrayList()
    private var currentCategorySubList: List<Category?>? = null
    private var currentQstIndex = 0
    private var userRightScore = 0
    private var userWrongScore = 0

    private var isAnswered = false

    private var currentQuestion: Question? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            categoryType = bundle.getString(Constants.TAG_CATEGORY_TYPE, Constants.VERB_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_categories_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = QuizCategoriesFragmentBinding.bind(view)
        soundManager = SoundManager()
        rbDefaultColorTxt = binding.QuizCategoryOption1.textColors

        binding.tvQuizChooseTheRightAnswerLabel.text = chooseTvQuizRightAnswerRequiredLanguage(
            Utils.nativeLanguage
        ) // Choose the right native Language

        fillListWithRequiredCategoryFromDataBase()
        ifCategoryIsSentenceOrIdiomAddMargins()
        initCountDownTimerAndStartIt()

        //-------------------------------------------------------------begin-----------------------------------------------------------------
        setRandomIndexesIncludingTheRightAnswerIndexThenGenerateRequiredQuestionList() // Random 3 indexes including the right index -> to choose random question list.

        showNextQuestion() // a new question set with there options + native element

        binding.btnConfirmNextCategory.setOnClickListener { v: View? ->    // the button Confirm/Next
            if (!isAnswered) checkAnswer()
            else {
                showNextQuestion()
            }
        }

        //-----------------------------------------------------------------------------------------------------------------------------------------
        txtRadioOptionToSpeech(binding.QuizCategoryOption1)
        txtRadioOptionToSpeech(binding.QuizCategoryOption2)
        txtRadioOptionToSpeech(binding.QuizCategoryOption3)

        val theQst = chooseTvQuizRightAnswerRequiredLanguage(Utils.nativeLanguage)
        val mainElementQst = binding.tvQuizMainElementQuestion.text.toString()
        val option1 = binding.QuizCategoryOption1.text.toString()
        val option2 = binding.QuizCategoryOption2.text.toString()
        val option3 = binding.QuizCategoryOption3.text.toString()

        val questionToShareTxt = ("""$theQst :
$mainElementQst
A.$option1
B.$option2
C.$option3

For additional questions or tests, please visit our app :
${getString(R.string.main_app_url)}""")
        binding.fabShareQstFriend.setOnClickListener { v: View? ->
            shareQst(
                questionToShareTxt
            )
        }
    }

    private fun ifCategoryIsSentenceOrIdiomAddMargins() {
        val margins = intArrayOf(0, 12, 0, 4)
        if (categoryType == Constants.IDIOM_NAME || categoryType == Constants.SENTENCE_NAME) {
            maxCounterTimer = 20
            val layoutParams1 =
                binding.QuizCategoryOption1.layoutParams as RadioGroup.LayoutParams
            layoutParams1.setMargins(margins[0], margins[1], margins[2], margins[3])
            val layoutParams2 =
                binding.QuizCategoryOption2.layoutParams as RadioGroup.LayoutParams
            layoutParams2.setMargins(margins[0], margins[1], margins[2], margins[3])
            val layoutParams3 =
                binding.QuizCategoryOption3.layoutParams as RadioGroup.LayoutParams
            layoutParams3.setMargins(margins[0], margins[1], margins[2], margins[3])
        }
    }

    private fun fillListWithRequiredCategoryFromDataBase() {
        val dbAccess = getInstance(requireActivity())
        dbAccess.openToRead()
        val allRequiredTypeListFromDB =
            ArrayList(dbAccess.getAllElementsCategoryList(categoryType, false))
        dbAccess.close()
        allRequiredTypeListFromDB.shuffle()
        currentCategorySubList = allRequiredTypeListFromDB.subList(0,Utils.maxQuestionsPerQuiz)
    }

    private fun initCountDownTimerAndStartIt() {
        if (countDownTimerHelper == null) {
            countDownTimerHelper = CountDownTimerHelper(maxCounterTimer * 1000L, 1000)
            countDownTimerHelper!!.setListener(this)
        }
    }

    //---------------------------------------------------------------------------------------------
    private fun checkEmptyAnswerCounter() {
        adsClickListener!!.onShowInterstitialAd()
        userWrongScore++
        binding.tvQuizUserWrongAnswerCounter.text = userWrongScore.toString()

        isAnswered = true
        binding.btnConfirmNextCategory.text = "Next"

        //  Random random = new Random();

        // int randomIndex = random.nextInt(Utils.phrasesIncorrectAnswers.size());
        // String text = Utils.phrasesIncorrectAnswers.get(randomIndex);
        val text = "You don't choose any answer , please make sure to choose an answer next time "
        speakEnglish(text)

        for (i in 0..<binding.quizRadioGroup.childCount) {
            val rbMaybeCorrect = binding.quizRadioGroup.getChildAt(i) as RadioButton
            val textMaybeCorrect = rbMaybeCorrect.text.toString()
            if (textMaybeCorrect == currentQuestion!!.rightAnswer) {
                rbMaybeCorrect.setTextColor(Color.GREEN)
            } else {
                rbMaybeCorrect.setTextColor(Color.RED)
            }
        }

        // the counter is reached the final Question then change the btn to finish
        if (currentQstIndex == Utils.maxQuestionsPerQuiz) {
            binding.btnConfirmNextCategory.text = "Finish"
            binding.btnConfirmNextCategory.setTextColor(Color.GREEN)
            binding.btnConfirmNextCategory.setBackgroundColor(Color.WHITE)
        }
    }

    private fun speakEnglish(text: String) {
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager?.speak(text)
        }
    }


    private fun setRandomIndexesIncludingTheRightAnswerIndexThenGenerateRequiredQuestionList() {
        val randomSet: MutableSet<Int?> = HashSet()
        val random = Random()

        for (i in currentCategorySubList!!.indices) {
            randomSet.clear() // Clear the set for each new iteration

            // Generate two random indices different from i
            while (randomSet.size < 2) {
                val randomIndex = random.nextInt(currentCategorySubList!!.size)
                if (randomIndex != i) {
                    randomSet.add(randomIndex)
                }
            }

            // Add i to the set
            randomSet.add(i)

            // Convert set to list for shuffling
            val randomList: List<Int?> = ArrayList(randomSet)
            Collections.shuffle(randomList)
            // Now randomList contains 3 unique indices: i and two others
            // You can use these indices to select questions from currentElementsList
            val mainNativeElement = choosingTheRightMainElementLang(
                Utils.nativeLanguage,
                i
            ) // here change the Element language
            val rbOption1 = currentCategorySubList!![randomList[0]!!]!!.categoryEng
            val rbOption2 = currentCategorySubList!![randomList[1]!!]!!.categoryEng
            val rbOption3 = currentCategorySubList!![randomList[2]!!]!!.categoryEng
            val rightAnswer = currentCategorySubList!![i]!!.categoryEng

            questionsList.add(
                Question(
                    mainNativeElement,
                    rbOption1,
                    rbOption2,
                    rbOption3,
                    rightAnswer
                )
            )
        }
    }


    private fun chooseTvQuizRightAnswerRequiredLanguage(nativeLanguage: String): String {
        return when (nativeLanguage) {
            Constants.LANGUAGE_NATIVE_ARABIC -> resources.getString(
                R.string.quiz_main_question_label_arabic
            )

            Constants.LANGUAGE_NATIVE_SPANISH -> resources.getString(
                R.string.quiz_main_question_label_spanish
            )

            else -> resources.getString(R.string.quiz_main_question_label_french)
        }
    }

    private fun choosingTheRightMainElementLang(nativeLanguage: String, currentIndex: Int): String {
        return when (nativeLanguage) {
            Constants.LANGUAGE_NATIVE_ARABIC -> currentCategorySubList!![currentIndex]!!.categoryAr

            Constants.LANGUAGE_NATIVE_SPANISH -> currentCategorySubList!![currentIndex]!!.categorySp

            else -> currentCategorySubList!![currentIndex]!!.categoryFr
        }
    }

    override fun onTick(secondsUntilFinished: Int) {
        binding.tvCounterDownTimer.text = secondsUntilFinished.toString()
        binding.progressBarTimer.progress =
            binding.progressBarTimer.progress + 100 / (maxCounterTimer - 1)
        if (secondsUntilFinished <= 5) {
            soundManager?.playSound(requireActivity(), R.raw.start_sound1)
            //binding.tvCounterDownTimer.setTextColor(Color.RED);
        }
    }

    override fun onFinish() {
        // Handle timer finished event
        counterDownTimerOver()
        val idCheckedRadio = binding.quizRadioGroup.checkedRadioButtonId
        if (idCheckedRadio == -1) {
            soundManager!!.playSound(requireActivity(), R.raw.error_sound2)
            checkEmptyAnswerCounter()
        } else {
            checkAnswer()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InteractionActivityFragmentsListener) {
            interactionListener = context
        }
        if (context is AdsClickListener) {
            adsClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        interactionListener = null
        adsClickListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        soundManager!!.release()

        if (countDownTimerHelper != null) {
            countDownTimerHelper!!.stop()
        }
    }

    private fun counterDownTimerOver() {
        val text = "Time over! "
        // Check if the TextToSpeech instance is initialized before using it.
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager?.speak(text)
        }
    }


    //---------------------------gpt function-------------------------------------------
    private fun showNextQuestion() {
        if (currentQstIndex < Utils.maxQuestionsPerQuiz) {
            isAnswered = false
            binding.fabShareQstFriend.isActivated = false
            resetUIForNextQuestion() // Reset UI Color & Timer & clean Check RadioButtons for the next question
            currentQuestion = questionsList[currentQstIndex]
            updateUIWithQuestion(currentQuestion!!)
            currentQstIndex++

            if (countDownTimerHelper != null) {
                binding.tvCounterDownTimer.text =
                    (maxCounterTimer / 1000).toString() // Update the TextView with the initial time (15 seconds)
                countDownTimerHelper!!.start()
            }
        } else {
            isAnswered = true
            binding.fabShareQstFriend.isActivated = true
            if (countDownTimerHelper != null) {
                countDownTimerHelper!!.stop()
            }
            finishQuiz()
        }
    }


    private fun resetUIForNextQuestion() {
        /*isAnswered = false;*/
        binding.progressBarTimer.progress = 0
        binding.btnConfirmNextCategory.setText(R.string.quiz_button_text_confirm) // changed R.string.confirm_text
        binding.QuizCategoryOption1.setTextColor(rbDefaultColorTxt)
        binding.QuizCategoryOption2.setTextColor(rbDefaultColorTxt)
        binding.QuizCategoryOption3.setTextColor(rbDefaultColorTxt)
        binding.quizRadioGroup.clearCheck()
    }

    private fun updateUIWithQuestion(question: Question) {
        binding.tvQuizMainElementQuestion.text = question.theMainElement
        binding.QuizCategoryOption1.text = question.option1
        binding.QuizCategoryOption2.text = question.option2
        binding.QuizCategoryOption3.text = question.option3
        binding.tvQuizCurrentIndex.text =
            (currentQstIndex + 1).toString() + "/" + Utils.maxQuestionsPerQuiz
    }

    private fun checkAnswer() {
        val checkedRadioID = binding.quizRadioGroup.checkedRadioButtonId
        if (checkedRadioID != -1) {
            isAnswered = true
            binding.fabShareQstFriend.isActivated = true

            if (countDownTimerHelper != null) {
                countDownTimerHelper!!.stop()
            } // Pause the timer when checking the answer

            binding.btnConfirmNextCategory.setText(R.string.quiz_button_text_next)
            val radioSelected = requireView().findViewById<RadioButton>(checkedRadioID)
            val userAnswer = radioSelected.text.toString()

            if (userAnswer == currentQuestion!!.rightAnswer) {
                handleCorrectAnswer()
            } else {
                handleIncorrectAnswer()
            }

            setAnsweredRequiredRadioButtonsColorText(currentQuestion!!.rightAnswer) //  right answer (green color) , wrong answers (red color).

            if (currentQstIndex == Utils.maxQuestionsPerQuiz) {
                binding.btnConfirmNextCategory.setText(R.string.quiz_button_text_finish)
                MainActivity.textToSpeechManager?.speak("Final Question!")
            }
        } else {
            handleNoAnswerSelected()
        }
    }

    private fun setAnsweredRequiredRadioButtonsColorText(rightAnswer: String) {
        for (i in 0..<binding.quizRadioGroup.childCount) {
            val radioButton = binding.quizRadioGroup.getChildAt(i) as RadioButton
            if (radioButton.text.toString() == rightAnswer) {
                radioButton.setTextColor(Color.GREEN)
            } else {
                radioButton.setTextColor(Color.RED)
            }
        }
    }

    private fun handleCorrectAnswer() {
        soundManager!!.playSound(requireActivity(), R.raw.coins_sound1)
        userRightScore++
        //  if you want you can add anim here!
        binding.tvQuizUserRightAnswerCounter.animation =
            AnimationUtils.loadAnimation(
                requireActivity(),
                R.anim.anim_tv_right_wrong_score
            )
        binding.tvQuizUserRightAnswerCounter.text = userRightScore.toString()

        val random = Random()
        val randomIndex = random.nextInt(Utils.phrasesCorrectAnswers.size)
        val text = Utils.phrasesCorrectAnswers[randomIndex]
        speakEnglish(text)
    }


    private fun handleIncorrectAnswer() {
        userWrongScore++
        soundManager!!.playSound(requireActivity(), R.raw.error_sound1)
        val randomIndex = Random().nextInt(Utils.phrasesIncorrectAnswers.size)
        val text = Utils.phrasesIncorrectAnswers[randomIndex]
        speakEnglish(text)

        binding.tvQuizUserWrongAnswerCounter.text = userWrongScore.toString()
        binding.tvQuizUserWrongAnswerCounter.animation =
            AnimationUtils.loadAnimation(
                requireActivity(),
                R.anim.anim_tv_right_wrong_score
            )
    }

    private fun handleNoAnswerSelected() {
        isAnswered = false
        binding.fabShareQstFriend.isActivated = false

        val text = getString(R.string.quiz_toast_text_no_answer_selected)
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager?.speak(text)
        }
    }


    private fun finishQuiz() {
        //  here you can finish the quiz with dialog and set scores...etc
        if (Utils.switchSimpleToVideoAds) {
            adsClickListener!!.onShowInterstitialAd()
        } else {
            adsClickListener!!.onShowRewardedAd()
        }
        Utils.switchSimpleToVideoAds = !Utils.switchSimpleToVideoAds

        if (countDownTimerHelper != null) countDownTimerHelper!!.stop()

        finishQuizUpdateAll(userRightScore)
    }

    private fun finishQuizUpdateAll(userScore: Int) {
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
        interactionListener!!.onSendScoresToDialog(
            categoryType,
            pointsAdded,
            elementsAdded,
            userRightScore,
            msg
        )
    }


    private fun finishQuizUpdateScoresCategory(
        categoryType: String,
        pointsAdded: Int,
        elementsAdded: Int
    ) {
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

    private fun txtRadioOptionToSpeech(radioOption: RadioButton) {
        radioOption.setOnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
            if (b) {
                val text = radioOption.text.toString()
                // Check if the TextToSpeech instance is initialized before using it.
                    MainActivity.textToSpeechManager?.speak(text)

            }
        }
    }


    private fun shareQst(qstToShare: String) {
        // Create an Intent with ACTION_SEND
        val shareIntent = Intent(Intent.ACTION_SEND)

        // Set the MIME type
        shareIntent.setType("text/plain")

        // Put the text to share
        shareIntent.putExtra(Intent.EXTRA_TEXT, qstToShare)

        // Create a chooser to let the user choose where to share
        val chooserIntent = Intent.createChooser(shareIntent, "Share with")

        // Check if there are apps that can handle the share intent
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            // Start the chooser activity
            startActivity(chooserIntent)
        }
    }

    companion object {
        fun getInstance(categoryType: String): QuizCategoriesFragment {
            val bundle = Bundle()
            bundle.putString(Constants.TAG_CATEGORY_TYPE, categoryType)
            val fragment = QuizCategoriesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}