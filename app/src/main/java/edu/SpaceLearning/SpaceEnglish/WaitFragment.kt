package edu.SpaceLearning.SpaceEnglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class WaitFragment : Fragment() {
    private var permissionScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            permissionScore = arguments!!.getInt(ARG_PERMISSION_SCORE)
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
        val tvWaitRequiredToOpen = view.findViewById<TextView>(R.id.tvScoreOpen)
        tvWaitRequiredToOpen.text = "Required $permissionScore points To Open this Table"
    }

    companion object {
        private const val ARG_PERMISSION_SCORE = "permission_score"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param permissionScore Required permission score to open a table
         * @return A new instance of fragment WaitFragment.
         */
        @JvmStatic
        fun newInstance(permissionScore: Int): WaitFragment {
            val fragment = WaitFragment()
            val args = Bundle()
            args.putInt(ARG_PERMISSION_SCORE, permissionScore)
            fragment.arguments = args
            return fragment
        }
    }
}


