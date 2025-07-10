package edu.SpaceLearning.SpaceEnglish

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * Fragment for displaying more apps and linking to their respective URLs.
 */
class MoreApps : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.more_apps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find buttons in the layout
        val btnApp2 = view.findViewById<Button>(R.id.btnMoreAppsTheSecond)
        val btnApp3 = view.findViewById<Button>(R.id.btnMoreAppsTheThird)

        // Set click listeners for each button
        btnApp2.setOnClickListener { v: View? -> toOurApp(getString(R.string.app1_url)) }
        btnApp3.setOnClickListener { v: View? -> toOurApp(getString(R.string.app2_url)) }
    }

    /**
     * Opens the specified app URL using an Intent.
     *
     * @param urlApp The URL of the app to open.
     */
    private fun toOurApp(urlApp: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlApp))
        startActivity(intent)
    }
}
