/*
 * File: HomeNavigationFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: This file contains the implementation of the HomeNavigationFragment class,
 *          which represents the home screen fragment of the SpaceEnglish app.
 *          It includes UI setup, interaction handling with shared preferences,
 *          image profile management, and integration with ViewPager2 for displaying
 *          information scores categorized by tabs.
 */
package edu.SpaceLearning.SpaceEnglish._Navigationfragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import edu.SpaceLearning.SpaceEnglish.Adapters.ScoreInfoViewPagerAdapter
import edu.SpaceLearning.SpaceEnglish.ScoreOverviewFragment
import edu.SpaceLearning.SpaceEnglish.Listeners.ActivityFragmentInteractionListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.QuizUtils
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentHomeNavigationBinding


class HomeNavigationFragment : Fragment() {
    private lateinit var binding: FragmentHomeNavigationBinding
    private  var activityFragmentListener: ActivityFragmentInteractionListener? = null
    private  val fragmentsForPager2InfoScores = ArrayList<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentHomeNavigationBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName ?: getString(R.string.home_user_default_name)
        val photoProfileUri = user?.photoUrl

        // Initialize UI components and set up interactions
        initializeHomeUI(userName,photoProfileUri) // Initialize UI elements like buttons, imageProfile, and user name
        setupTabsWithPager2() // Set up tabs with ViewPager2 for info scores
    }

    private fun initializeHomeUI(userName : String, photoProfileUri : Uri?) {
        setImageProfile(photoProfileUri)
        setUserName(userName)
        binding.btnHomeGoToLearn.setOnClickListener { v: View? -> activityFragmentListener?.onHomeStartClicked(Constants.NAV_INDEX_TABLE) }
        binding.btnHomeGoToQuiz.setOnClickListener { v: View? -> activityFragmentListener?.onHomeStartClicked(Constants.NAV_INDEX_QUIZ) }
    }

    private fun setupTabsWithPager2() {
        // Initialize fragments for ViewPager2 and set up adapter with TabLayoutMediator
        for (categoryName in QuizUtils.quizCategoryNames) {
            fragmentsForPager2InfoScores.add(ScoreOverviewFragment.newInstance(categoryName))
        }
        val scoreInfoViewPagerAdapter =
            ScoreInfoViewPagerAdapter(requireActivity(), fragmentsForPager2InfoScores)
        binding.infoScoresPager2.adapter = scoreInfoViewPagerAdapter
        TabLayoutMediator(binding.infoTabLayout, binding.infoScoresPager2)
        {
            tab: TabLayout.Tab, position: Int -> tab.text = QuizUtils.quizCategoryNames[position]   // Set tab text based on category names
        }.attach()
    }

    private fun setImageProfile(photoUri : Uri?) {
        Glide.with(requireContext())
            .load(photoUri)
            .circleCrop()
            .placeholder(R.drawable.ic_person_24) // optional placeholder image
            .error(R.drawable.ic_person_24)       // optional error image
            .into(binding.imgHomeProfile)          // your ImageView reference
    }

    private fun setUserName(userName : String) {
        binding.tvHomeUserName.text = getString(R.string.home_msg_hi) + userName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the context implements ActivityFragmentInteractionListener
        if (context is ActivityFragmentInteractionListener) {
            activityFragmentListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        // Release the interaction listener to avoid memory leaks
        activityFragmentListener = null
    }
}
