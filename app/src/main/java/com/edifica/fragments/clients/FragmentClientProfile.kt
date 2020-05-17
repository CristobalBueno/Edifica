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
import kotlinx.android.synthetic.main.fragment_client_profile.*

/**
 * A simple [Fragment] subclass.
 */
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

        // TODO SANTANA
        // TODO Arreglar esto
        // TODO Fuera dataholder, recoger las cosas de la memoria interna
        // TODO El cliente no tiene foto
        client_profile_name.text = Dataholder.name
        client_profile_phone.text = Dataholder.phone
        client_profile_email.text = Dataholder.email
        client_profile_image.setImageURI(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client_profile_ads.setOnClickListener {
            (activity as ActivityClientMain).gotoActivity(ActivityClientAds())
        }
        client_profile_mod.setOnClickListener {
            (activity as ActivityClientMain).gotoActivity(ActivityClientProfileMod())
        }
        client_profile_delete.setOnClickListener {
            (activity as ActivityClientMain).gotoActivity(ActivityAnimation())
            (activity as ActivityClientMain).finish()
        }
    }


}