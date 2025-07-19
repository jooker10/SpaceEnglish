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
import edu.SpaceLearning.SpaceEnglish.Adapters.InfoScorePager2Adapter
import edu.SpaceLearning.SpaceEnglish.HomeInfoScoresPager2Fragment
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.R
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

        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName ?: "User 001"
        val photoProfileUri = user?.photoUrl

        // Initialize UI components and set up interactions
        initUIHomePage(userName,photoProfileUri) // Initialize UI elements like buttons, imageProfile, and user name
        setUpTabsWithPager2InfoScores() // Set up tabs with ViewPager2 for info scores
    }

    private fun initUIHomePage(userName : String , photoProfileUri : Uri?) {
        setImageProfile(photoProfileUri)
        setUserName(userName)
        binding.btnHomeGoToLearn.setOnClickListener { v: View? -> interactionListener?.onHomeGetStarted(Constants.TABLE_NAV_INDEX) }
        binding.btnHomeGoToQuiz.setOnClickListener { v: View? -> interactionListener?.onHomeGetStarted(Constants.QUIZ_NAV_INDEX) }
        binding.tvHomeUserName.text = "Hi, ${Utils.userName}"
    }

    private fun setUpTabsWithPager2InfoScores() {
        // Initialize fragments for ViewPager2 and set up adapter with TabLayoutMediator
        for (categoryName in Utils.tableCategoriesListNames) {
            fragmentsForPager2InfoScores.add(HomeInfoScoresPager2Fragment.newInstance(categoryName))
        }
        val infoScorePager2Adapter = InfoScorePager2Adapter(requireActivity(), fragmentsForPager2InfoScores)
        binding.infoScoresPager2.adapter = infoScorePager2Adapter
        TabLayoutMediator(binding.infoTabLayout, binding.infoScoresPager2)
        {
            tab: TabLayout.Tab, position: Int -> tab.text = Utils.tableCategoriesListNames[position]   // Set tab text based on category names
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

    private fun setUserName(name : String) {
        binding.tvHomeUserName.text = name
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
