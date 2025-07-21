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
import android.widget.Toast
import androidx.fragment.app.Fragment
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbManager.Companion.getInstance
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.Listeners.UiControllerListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Question
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SoundManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentQuizCategoriesInnerBinding


class QuizCategoriesSubFragment : Fragment(), UiControllerListener {   /*OnCountdownListener*/
    private lateinit var binding: FragmentQuizCategoriesInnerBinding
    private var interactionListener: InteractionActivityFragmentsListener? = null
    private var adsClickListener: AdsClickListener? = null
    private var soundManager: SoundManager? = null

    private lateinit var quizManager: QuizManager
   // private val maxAllowedAddedPoints = 10

    private var categoryType: String = ""

    private var rbDefaultColorTxt: ColorStateList? = null

    private var questionsList: MutableList<Question> = ArrayList()
    private lateinit var currentSpecificCategorySubList: List<Category>

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

        binding.tvQuestionLabel.text = setAppNativeLanguage(Utils.nativeLanguage) // Choose the app native Language

        currentSpecificCategorySubList = getRequiredCategoryData()   // get the required List of a specific category (ex: Verbs...) - the number of elements: Utils.maxQuestionsPerQuiz

        setLayoutMarginsOfSomeCategory()
        //initCountDownTimerAndStartIt()

        quizManager = QuizManager(requireActivity(), this, categoryType, currentSpecificCategorySubList)
        questionsList = quizManager.generateRequiredQuestionsList()

        quizManager.setTimerListener(object : QuizManager.TimerListener {
            override fun onTick(secondsLeft: Int) {
                binding.tvCounterDownTimer.text = "$secondsLeft"
                binding.progressBarTimer.progress =
                    binding.progressBarTimer.progress + 100 / (quizManager.maxCounterTimer - 1)
                if (secondsLeft <= 5) {
                    soundManager?.playSound(requireActivity(), R.raw.start_sound1)
                    //binding.tvCounterDownTimer.setTextColor(Color.RED);
                }
            }

            override fun onTimeFinished() {
                Toast.makeText(requireActivity(),"Time's up!",Toast.LENGTH_LONG).show()
            }

        })

        quizManager.moveToNextQuestion()

        binding.btnConfirmNextCategory.setOnClickListener {

            if (!QuizManager.isAnswered) {
                quizManager.checkAnswer()
            } else {
                quizManager.moveToNextQuestion()
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
            //maxCounterTimer = 20
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
        val getRequiredCategoryDbList = ArrayList(dbManager.getRequiredCategoryDataOf(categoryType, false))
        dbManager.close()
        getRequiredCategoryDbList.shuffle()
        return getRequiredCategoryDbList.subList(0, Utils.maxQuestionsPerQuiz)
    }

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

            else -> currentSpecificCategorySubList[currentIndex].frCategory
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
        soundManager?.release()

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

    override fun resetUIForNextQuestion() {
        binding.also {
            it.progressBarTimer.progress = 0
            it.btnConfirmNextCategory.setText(R.string.quiz_button_text_confirm) // changed R.string.confirm_text
            it.QuizCategoryOption1.setTextColor(rbDefaultColorTxt)
            it.QuizCategoryOption2.setTextColor(rbDefaultColorTxt)
            it.QuizCategoryOption3.setTextColor(rbDefaultColorTxt)
            it.quizRadioGroup.clearCheck()
        }

    }

    override fun updateUIWithQuestion(question: Question,currentQstIndex : Int) {
        binding.tvQuizMainElementQuestion.text = question.theMainElement
        binding.QuizCategoryOption1.text = question.optionsList[0]
        binding.QuizCategoryOption2.text = question.optionsList[1]
        binding.QuizCategoryOption3.text = question.optionsList[2]
        binding.tvQuizCurrentIndex.text = "$currentQstIndex/${Utils.maxQuestionsPerQuiz}"
    }

    override fun updateUiRightScore(userRightScore : Int) {

        binding.tvQuizUserRightAnswerCounter.animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_tv_right_wrong_score)
        binding.tvQuizUserRightAnswerCounter.text = userRightScore.toString()
    }
    override fun updateUiWrongScore(userWrongScore : Int) {
        binding.tvQuizUserWrongAnswerCounter.text = userWrongScore.toString()
        binding.tvQuizUserWrongAnswerCounter.animation =
            AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_tv_right_wrong_score)
    }

    override fun getCheckedOptionUiID(): Int {
        return binding.quizRadioGroup.checkedRadioButtonId
    }

    override fun getCheckedOptionUi(checkedRadioID : Int): RadioButton {
        return requireView().findViewById(checkedRadioID)

    }

    override fun updateNextButtonText(text : String) {
        binding.btnConfirmNextCategory.text = text
    }

    override fun updateOptionsUiAfterAnswers(rightAnswer : String) {
        for (i in 0..<binding.quizRadioGroup.childCount) {
            val rbMaybeCorrect = binding.quizRadioGroup.getChildAt(i) as RadioButton
            val textMaybeCorrect = rbMaybeCorrect.text.toString()
            if (textMaybeCorrect == rightAnswer) {
                rbMaybeCorrect.setTextColor(Color.GREEN)
            } else {
                rbMaybeCorrect.setTextColor(Color.RED)
            }
        }
    }


    override fun changeRightWrongAnswerColors(rightAnswer: String) {
        for (i in 0..<binding.quizRadioGroup.childCount) {
            val radioButton = binding.quizRadioGroup.getChildAt(i) as RadioButton
            if (radioButton.text.toString() == rightAnswer) {
                radioButton.setTextColor(Color.GREEN)
            } else {
                radioButton.setTextColor(Color.RED)
            }
        }
    }

}