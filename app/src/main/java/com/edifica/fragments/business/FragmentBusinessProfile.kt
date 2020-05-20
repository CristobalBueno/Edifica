package com.edifica.fragments.business

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edifica.R
import com.edifica.activities.business.ActivityBusinessMain
import com.edifica.activities.business.ActivityBusinessProfileMod
import com.edifica.activities.business.ActivityBusinessProfileBudgets
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.activities.login.ActivityAnimation
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_business_profile.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class FragmentBusinessProfile : Fragment() {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_profile, container, false)
    }

    override fun onResume() {
        super.onResume()
        /*//TODO sincronizar de base de datos
        business_profile_name.text = DataHolder.name
        business_profile_phone.text = DataHolder.phone
        business_profile_email.text = DataHolder.email
        business_profile_image.setImageURI(DataHolder.photo)*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDefaultLayout()

        business_profile_ads.setOnClickListener {
            (activity as ActivityBusinessMain).gotoActivity(ActivityBusinessProfileBudgets())
        }
        business_profile_mod.setOnClickListener {
            (activity as ActivityBusinessMain).gotoActivity(ActivityBusinessProfileMod())
        }
        business_profile_log_out.setOnClickListener {

            Token.deleteToken(File(context?.filesDir, Dataholder.FILENAME))
            auth.signOut()

            (activity as ActivityBusinessMain).gotoActivity(ActivityAnimation())
            (activity as ActivityBusinessMain).finish()
        }
    }

    private fun setDefaultLayout() {
        business_profile_name.setText(R.string.fragment_business_profile_name)
        business_profile_phone.setText(R.string.fragment_business_profile_phone)
        business_profile_email.setText(R.string.fragment_business_profile_email)
        business_profile_web.setText(R.string.fragment_business_profile_web)
        business_profile_ads.setText(R.string.fragment_business_profile_ads)
        business_profile_mod.setText(R.string.fragment_business_profile_mod)
        business_profile_log_out.setText(R.string.fragment_business_profile_log_out)
    }
}