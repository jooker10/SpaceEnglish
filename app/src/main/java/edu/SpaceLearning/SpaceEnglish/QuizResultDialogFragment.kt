package edu.SpaceLearning.SpaceEnglish

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import edu.SpaceLearning.SpaceEnglish.Listeners.ActivityFragmentInteractionListener
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.QuizUtils
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity
import edu.SpaceLearning.SpaceEnglish.databinding.DialogQuizScoresBinding

/**
 * QuizResultDialogFragment is a dialog fragment that displays quiz scores and options to start a new quiz or return home.
 */
class QuizResultDialogFragment : DialogFragment() {
    private var quizInteractionListener: ActivityFragmentInteractionListener? = null

    private lateinit var binding: DialogQuizScoresBinding
    private var categoryType: String = ""
    private var resultMessage: String = ""
    private var pointsAdded = 0
    private var elementsAdded = 0
    private var userRightScore = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityFragmentInteractionListener) {
            quizInteractionListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        quizInteractionListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            categoryType = bundle.getString(ARG_CATEGORY_TYPE,"")
            pointsAdded = bundle.getInt(ARG_POINTS_ADDED)
            elementsAdded = bundle.getInt(ARG_ELEMENTS_ADDED)
            userRightScore = bundle.getInt(ARG_USER_RIGHT_SCORE)
            resultMessage = bundle.getString(ARG_MSG,"")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_quiz_scores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogQuizScoresBinding.bind(view)

        // Set text and events for UI elements
        binding.tvDialogCongrat.text = resultMessage // Set congratulatory message
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager!!.speak(resultMessage) // Speak congratulatory message
        }

        binding!!.tvDialogResult.text =
            userRightScore.toString() + "/" + QuizUtils.maxQuestionsInQuiz // Display quiz result
        binding.tvPointsAdded.text = pointsAdded.toString() // Display points added
        binding.tvElementsAdded.text = elementsAdded.toString() // Display elements added
        binding.tvDialogCategoryElementAdded.text =
            "$categoryType Added :" // Display category type

        // Button click listeners
        binding.btnNewQuiz.setOnClickListener { v: View? ->
            quizInteractionListener!!.onStartNewQuizClicked() // Notify listener to start a new quiz
            dismiss() // Dismiss the dialog
        }

        binding.btnSendHome.setOnClickListener { view1: View? ->
            quizInteractionListener!!.onDialogReturnHomeClicked(categoryType) // Notify listener to send home with category type
            dismiss() // Dismiss the dialog
        }
    }

    companion object {
        // Argument keys for fragment arguments
        private const val ARG_CATEGORY_TYPE = "category"
        private const val ARG_MAIN_SCORE = "arg_main_score"
        private const val ARG_POINTS_ADDED = "arg_points_added"
        private const val ARG_ELEMENTS_ADDED = "arg_elements_added"
        private const val ARG_USER_RIGHT_SCORE = "arg_quiz_counter"
        private const val ARG_MSG = "msg"

        const val TAG: String = "quizDialog"

        /**
         * Creates a new instance of QuizResultDialogFragment with provided arguments.
         *
         * @param categoryType   The category type of the quiz.
         * @param pointsAdded    Points added in the quiz.
         * @param elementsAdded  Elements added during the quiz.
         * @param userRightScore Number of questions answered correctly.
         * @param msg            Message to display in the dialog.
         * @return A new instance of QuizResultDialogFragment.
         */

        fun newInstance(
            categoryType: String?,
            pointsAdded: Int,
            elementsAdded: Int,
            userRightScore: Int,
            msg: String?
        ): QuizResultDialogFragment {
            val bundle = Bundle()
            bundle.putString(ARG_CATEGORY_TYPE, categoryType)
            bundle.putInt(ARG_POINTS_ADDED, pointsAdded)
            bundle.putInt(ARG_ELEMENTS_ADDED, elementsAdded)
            bundle.putInt(ARG_USER_RIGHT_SCORE, userRightScore)
            bundle.putString(ARG_MSG, msg)

            val dialog_fragment = QuizResultDialogFragment()
            dialog_fragment.arguments = bundle
            return dialog_fragment
        }
    }
}
