package edu.SpaceLearning.SpaceEnglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class InsufficientScoreFragment : Fragment() {
    private var requiredScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            requiredScore = bundle.getInt(ARG_REQUIRED_SCORE,0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wait, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize and set the text for the TextView
        val scoreInfoTextView = view.findViewById<TextView>(R.id.tvScoreOpen)
        scoreInfoTextView.text = getString(R.string.msg_required_points_to_unlock_this_section) + requiredScore
    }

    companion object {
        private const val ARG_REQUIRED_SCORE = "required_score"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param permissionScore Required permission score to open a table
         * @return A new instance of fragment InsufficientScoreFragment.
         */
        @JvmStatic
        fun newInstance(permissionScore: Int): InsufficientScoreFragment {
            val fragment = InsufficientScoreFragment()
            val args = Bundle()
            args.putInt(ARG_REQUIRED_SCORE, permissionScore)
            fragment.arguments = args
            return fragment
        }
    }
}


