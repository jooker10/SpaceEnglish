/*
 * File: HomeNavFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: This file contains the implementation of the HomeNavFragment class,
 *          which represents the home screen fragment of the SpaceEnglish app.
 *          It includes UI setup, interaction handling with shared preferences,
 *          image profile management, and integration with ViewPager2 for displaying
 *          information scores categorized by tabs.
 */
package edu.SpaceLearning.SpaceEnglish._Navfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.SpaceLearning.SpaceEnglish.Adapters.InfoScorePager2Adapter
import edu.SpaceLearning.SpaceEnglish.HomeInfoScoresPager2Fragment.Companion.newInstance
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish.databinding.HomeNavFragmentBinding

class HomeNavFragment : Fragment() {
    private lateinit var binding: HomeNavFragmentBinding
    private  var interactionListener: InteractionActivityFragmentsListener? = null
    private  val fragmentsForPager2InfoScores = ArrayList<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        binding = HomeNavFragmentBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI components and set up interactions
        initHomeSharedPrefs() // Set up shared preferences related to HomeNavFragment
        initUIHome() // Initialize UI elements like buttons, imageProfile, and title
        setUpTabsWithPager2InfoScores() // Set up tabs with ViewPager2 for info scores
    }

    private fun initUIHome() {
        // Set click listener for image profile and display image if available

      //  binding.imgHomeProfile.setOnClickListener { v: View? -> interactionListener!!.onPickImageProfile() }
        if (Utils.uriProfile != null && !Utils.uriProfile.toString().isEmpty()) {
            binding.imgHomeProfile.setImageURI(Utils.uriProfile)
        }
        binding.imgHomeProfile.setOnClickListener {
            interactionListener?.onPickImageProfile()
            Toast.makeText(requireContext(), "Image Profile", Toast.LENGTH_SHORT).show()
        }

        binding.btnHomeGoToLearn.setOnClickListener { v: View? ->
            interactionListener?.onHomeGetStarted(
                Constants.TABLE_NAV_INDEX
            )
        }
        binding.btnHomeGoToQuiz.setOnClickListener { v: View? ->
            interactionListener?.onHomeGetStarted(
                Constants.QUIZ_NAV_INDEX
            )
        }
        binding.tvHomeUserName.text = "Hi, ${Utils.userName}"
    }

    private fun initHomeSharedPrefs() {
        // Retrieve user name from shared preferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        Utils.userName = sharedPreferences.getString(Constants.KEY_PREF_USER_NAME, "User 01")!!
    }

    private fun setUpTabsWithPager2InfoScores() {
        // Initialize fragments for ViewPager2 and set up adapter with TabLayoutMediator
        for (categoryName in Utils.tableListNames) {
            fragmentsForPager2InfoScores.add(newInstance(categoryName))
        }
        val infoScorePager2Adapter =
            InfoScorePager2Adapter(requireActivity(), fragmentsForPager2InfoScores)
        binding.infoScoresPager2.adapter = infoScorePager2Adapter
        TabLayoutMediator(
            binding.infoTabLayout, binding.infoScoresPager2
        ) { tab: TabLayout.Tab, position: Int ->
            // Set tab text based on category names
            tab.text = Utils.tableListNames[position]
        }.attach()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the context implements InteractionActivityFragmentsListener
        if (context is InteractionActivityFragmentsListener) {
            interactionListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        // Release the interaction listener to avoid memory leaks
        interactionListener = null
    }
}
