/*
 * File: QuizCategoriesSubFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment for displaying and managing quiz questions within specified categories.
 */
package edu.SpaceLearning.SpaceEnglish.QuizFiles

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
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbManager.Companion.getInstance
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
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentQuizCategoriesInnerBinding
import java.util.Collections
import java.util.Locale
import java.util.Random
import kotlin.math.min

class QuizCategoriesSubFragment : Fragment()/*, OnCountdownListener*/ {
    private lateinit var binding: FragmentQuizCategoriesInnerBinding
    private var interactionListener: InteractionActivityFragmentsListener? = null
    private var adsClickListener: AdsClickListener? = null
    private var soundManager: SoundManager? = null

    private lateinit var quizManager: QuizManager

    // private var countDownTimerHelper: CountDownTimerHelper? = null
    private var maxCounterTimer = 15
    private val maxAllowedAddedPoints = 10

    private var categoryType: String = ""

    private var rbDefaultColorTxt: ColorStateList? = null

    private var questionsList: MutableList<Question> = ArrayList()
    private lateinit var currentSpecificCategorySubList: List<Category>
    private var currentQstIndex = 0
    private var userRightScore = 0
    private var userWrongScore = 0

    private var isAnswered = false

    private lateinit var currentQuestion: Question

    companion object {
        fun getInstance(categoryType: String): QuizCategoriesSubFragment {
            val bundle = Bundle()
            bundle.putString(Constants.TAG_CATEGORY_TYPE, categoryType)
            val fragment = QuizCategoriesSubFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

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
        return inflater.inflate(R.layout.fragment_quiz_categories_inner, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuizCategoriesInnerBinding.bind(view)

        soundManager = SoundManager()
        rbDefaultColorTxt = binding.QuizCategoryOption1.textColors

        binding.tvQuestionLabel.text =
            setAppNativeLanguage(Utils.nativeLanguage) // Choose the app native Language

        currentSpecificCategorySubList =
            getRequiredCategoryData()   // get the required List of a specific category (ex: Verbs...) - the number of elements: Utils.maxQuestionsPerQuiz
        setLayoutMarginsOfSomeCategory()
        initCountDownTimerAndStartIt()

        quizManager =
            QuizManager(requireActivity(), binding, categoryType, currentSpecificCategorySubList)
        //generateRequiredQuestionsList() // Random 3 indexes including the right index -> to choose random question list.
        questionsList = quizManager.generateRequiredQuestionsList()

        //showNextQuestion() // a new question set with there options + native element
        quizManager.showNextQuestion()

        /*binding.btnConfirmNextCategory.setOnClickListener { v: View? ->    // the button Confirm/Next
            if (!isAnswered) {
                checkAnswer()
            } else {
                showNextQuestion()
            }
        }*/
        binding.btnConfirmNextCategory.setOnClickListener {
            if (!QuizManager.isAnswered) {
                quizManager.checkAnswer()
            } else {
                quizManager.showNextQuestion()
            }
        }

        //-----------------------------------------------------------------------------------------------------------------------------------------
        /*txtRadioOptionToSpeech(binding.QuizCategoryOption1)
        txtRadioOptionToSpeech(binding.QuizCategoryOption2)
        txtRadioOptionToSpeech(binding.QuizCategoryOption3)*/

        val theQst = setAppNativeLanguage(Utils.nativeLanguage)
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

    private fun setLayoutMarginsOfSomeCategory() {
        val margins = intArrayOf(0, 12, 0, 4)
        if (categoryType == Constants.IDIOM_NAME || categoryType == Constants.SENTENCE_NAME) {
            maxCounterTimer = 20
            val layoutParams1 =
                binding.QuizCategoryOption1.layoutParams as RadioGroup.LayoutParams
            layoutParams1.setMargins(margins[0], margins[1], margins[2], margins[3])
            val layoutParams2 =
                binding.QuizCategoryOption2.layoutParams as RadioGroup.LayoutParams
            layoutParams2.setMargins(margins[0], margins[1], margins[2], margins[3])
            val layoutParams =
                binding.QuizCategoryOption3.layoutParams as RadioGroup.LayoutParams
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3])
        }
    }

    private fun getRequiredCategoryData(): List<Category> {
        val dbManager = getInstance(requireActivity())
        dbManager.openToRead()
        val getRequiredCategoryDbList =
            ArrayList(dbManager.getRequiredCategoryDataOf(categoryType, false))
        dbManager.close()
        getRequiredCategoryDbList.shuffle()
        return getRequiredCategoryDbList.subList(0, Utils.maxQuestionsPerQuiz)
    }

    /* private fun initCountDownTimerAndStartIt() {
        if (countDownTimerHelper == null) {
            countDownTimerHelper = CountDownTimerHelper(maxCounterTimer * 1000L, 1000)
            countDownTimerHelper?.setListener(this)
        }
    }*/
    private fun initCountDownTimerAndStartIt() {
        if (QuizManager.countDownTimerHelper == null) {
            QuizManager.countDownTimerHelper = CountDownTimerHelper(maxCounterTimer * 1000L, 1000)
            QuizManager.countDownTimerHelper?.setListener(quizManager as OnCountdownListener?)
        }
    }

    //---------------------------------------------------------------------------------------------
    private fun checkEmptyAnswerCounter() {
        adsClickListener?.onShowInterstitialAd()
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
            MainActivity.textToSpeechManager?.setLanguage(Locale.ENGLISH)
            MainActivity.textToSpeechManager?.speak(text)
        }
    }


    /*private fun generateRequiredQuestionsList() {
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
            val rightAnswer = currentSpecificCategorySubList[i].engCategory

            questionsList.add(Question(mainElementQuestion, rbOption1, rbOption2, rbOption3, rightAnswer)
            )
        }
    }*/


    private fun setAppNativeLanguage(nativeLanguage: String): String {
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

    private fun setNativeMainElement(nativeLanguage: String, currentIndex: Int): String {
        return when (nativeLanguage) {
            Constants.LANGUAGE_NATIVE_ARABIC -> currentSpecificCategorySubList[currentIndex].arCategory

            Constants.LANGUAGE_NATIVE_SPANISH -> currentSpecificCategorySubList[currentIndex].spCategory

            else -> currentSpecificCategorySubList[currentIndex].FrCategory
        }
    }

   /* override fun onTick(secondsUntilFinished: Int) {
        binding.tvCounterDownTimer.text = secondsUntilFinished.toString()
        binding.progressBarTimer.progress =
            binding.progressBarTimer.progress + 100 / (maxCounterTimer - 1)
        if (secondsUntilFinished <= 5) {
            soundManager?.playSound(requireActivity(), R.raw.start_sound1)
            //binding.tvCounterDownTimer.setTextColor(Color.RED);
        }
    }*/


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

        if (QuizManager.countDownTimerHelper != null) {
            QuizManager.countDownTimerHelper?.stop()
        }
    }

    /*private fun counterDownTimerOver() {
        val text = "Time over! "
        // Check if the TextToSpeech instance is initialized before using it.
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager?.speak(text)
        }
    }
*/

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

}