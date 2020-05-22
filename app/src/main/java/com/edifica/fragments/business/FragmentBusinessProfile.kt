package com.edifica.fragments.business

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edifica.R
import com.edifica.activities.business.ActivityBusinessMain
import com.edifica.activities.business.ActivityBusinessProfileMod
import com.edifica.activities.login.ActivityAnimation
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.edifica.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_business_profile.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class FragmentBusinessProfile : Fragment() {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        updateView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDefaultLayout()

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

    fun updateView() {
        val query = db.collection("users").document(auth.currentUser?.uid.toString())
        query.get().addOnSuccessListener { document ->
            if (document != null) {
                var myUser = document.toObject(User::class.java)
                if (myUser != null) {
                    business_profile_name.text = myUser.name
                    business_profile_phone.text = myUser.phone
                    business_profile_email.text = myUser.email
                    business_profile_web.text = myUser.web
                    if (myUser.image != ""){
                        Picasso.get().load(myUser.image).into(business_profile_image)
                    }else{
                        Picasso.get().load("https://static.ferrovial.com/wp-content/uploads/sites/4/2018/01/22210053/ferrovial-1920x1080.jpg").into(business_profile_image)
                    }
                }
            } else {
                Log.d("miapp", "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("miapp", "get failed with ", exception)
        }
    }

    private fun setDefaultLayout() {
        business_profile_name.setText(R.string.fragment_business_profile_name)
        business_profile_phone.setText(R.string.fragment_business_profile_phone)
        business_profile_email.setText(R.string.fragment_business_profile_email)
        business_profile_web.setText(R.string.fragment_business_profile_web)
        business_profile_mod.setText(R.string.fragment_business_profile_mod)
        business_profile_log_out.setText(R.string.fragment_business_profile_log_out)
    }
}