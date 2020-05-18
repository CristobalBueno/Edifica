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
import kotlinx.android.synthetic.main.fragment_client_profile.*

class FragmentClientProfile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View = inflater.inflate(R.layout.fragment_client_profile, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        /* TODO actualizar con servidor
        client_profile_name.text = server.name
        client_profile_phone.text = server.phone
        client_profile_email.text = server.email */
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDefaultLayout()

        client_profile_ads.setOnClickListener {
            (activity as ActivityClientMain).gotoActivity(ActivityClientAds())
        }
        client_profile_mod.setOnClickListener {
            (activity as ActivityClientMain).gotoActivity(ActivityClientProfileMod())
        }
        client_profile_log_out.setOnClickListener {
            //TODO log outuser
            (activity as ActivityClientMain).gotoActivity(ActivityAnimation())
            (activity as ActivityClientMain).finish()
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