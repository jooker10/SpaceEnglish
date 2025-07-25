package edu.SpaceLearning.SpaceEnglish

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.core.net.toUri

/**
 * Fragment for displaying more apps and linking to their respective URLs.
 */
class MoreAppsFragment : Fragment() {
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
        val btnOpenFirstApp = view.findViewById<Button>(R.id.btnMoreAppsTheSecond)
        val btnOpenSecondApp = view.findViewById<Button>(R.id.btnMoreAppsTheThird)

        // Set click listeners for each button
        btnOpenFirstApp.setOnClickListener { v: View? -> openAppUrl(getString(R.string.app1_url)) }
        btnOpenSecondApp.setOnClickListener { v: View? -> openAppUrl(getString(R.string.app2_url)) }
    }

    /**
     * Opens the specified app URL using an Intent.
     *
     * @param urlApp The URL of the app to open.
     */
    private fun openAppUrl(urlApp: String) {
        val intent = Intent(Intent.ACTION_VIEW, urlApp.toUri())
        startActivity(intent)
    }
}
