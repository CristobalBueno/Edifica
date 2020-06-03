package com.edifica.fragments.clients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edifica.R
import com.edifica.activities.clients.ActivityClientProfileMod
import com.edifica.activities.clients.ActivityClientAds
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.activities.login.ActivityAnimation
import com.edifica.models.Dataholder
import com.edifica.models.Token.Companion.deleteToken
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_client_profile.*
import java.io.File


class FragmentClientProfile : Fragment() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var activityMain: ActivityClientMain

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityMain = activity as ActivityClientMain

        return inflater.inflate(R.layout.fragment_client_profile, container, false)
    }

    override fun onResume() {

        super.onResume()
        val userToken = activityMain.userToken

        client_profile_name.text = userToken.name
        client_profile_phone.text = userToken.phone
        client_profile_email.text = userToken.email
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDefaultLayout()

        client_profile_ads.setOnClickListener {
            activityMain.gotoActivity(ActivityClientAds())
        }
        client_profile_mod.setOnClickListener {
            activityMain.gotoActivity(ActivityClientProfileMod())
        }
        client_profile_log_out.setOnClickListener {

            deleteToken(File(context?.filesDir, Dataholder.FILENAME))
            auth.signOut()

            activityMain.gotoActivity(ActivityAnimation())
            activityMain.finish()
        }
    }

    private fun setDefaultLayout() {
        client_profile_name.setText(R.string.fragment_client_profile_name)
        client_profile_phone.setText(R.string.fragment_client_profile_phone)
        client_profile_email.setText(R.string.fragment_client_profile_email)
        client_profile_ads.setText(R.string.fragment_client_profile_ads)
        client_profile_log_out.setText(R.string.fragment_client_profile_logout)
        client_profile_mod.setText(R.string.fragment_client_profile_mod_profile)
    }
}