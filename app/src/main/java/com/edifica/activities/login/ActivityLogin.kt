package com.edifica.activities.login

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.activities.business.ActivityBusinessMain
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.fragments.login.FragmentLogin
import com.edifica.fragments.login.FragmentLoginChoice
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.edifica.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File

class ActivityLogin : BaseActivity() {

    private val ISCLIENT = "isClient"

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        viewPage()
    }

    private fun viewPage() {

        val bundle = Bundle()

        supportActionBar?.elevation = 0.0f


        val myAdapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        bundle.putBoolean(ISCLIENT, true)
                        val fragLoginClient = FragmentLogin()
                        fragLoginClient.arguments = bundle
                        fragLoginClient
                    }
                    1 -> {
                        FragmentLoginChoice()
                    }
                    2 -> {
                        bundle.putBoolean(ISCLIENT, false)
                        val fragLoginClient = FragmentLogin()
                        fragLoginClient.arguments = bundle
                        fragLoginClient
                    }
                    else -> {
                        FragmentLoginChoice()
                    }
                }
            }


            override fun getItemCount(): Int {
                return 3
            }
        }

        mainViewPager2.adapter = myAdapter
        indicator.setViewPager(mainViewPager2)

        mainViewPager2.setCurrentItem(1, false)
    }

    private fun clientStart(user: Token) {
        if (user.identifier == 0) {
            Handler().postDelayed(Runnable {
                gotoActivity(ActivityClientMain())
            }, 2000)
        } else {
            Handler().postDelayed(Runnable {
                gotoActivity(ActivityBusinessMain())
            }, 2000)
        }
    }

    // Create User
    fun createUser(user: Token) {

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(
                        this,
                        getString(R.string.login_client_success),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    user.uid = auth.uid!!
                    user.saveToken(File(filesDir, Dataholder.FILENAME))

                    val dbUser = User(user.email, user.identifier, user.name, user.phone)

                    db = FirebaseFirestore.getInstance()
                    auth = FirebaseAuth.getInstance()

                    db.collection("users").document(auth.currentUser?.uid!!)
                        .set(dbUser)
                        .addOnSuccessListener { _ ->
                            clientStart(user)
                            Log.d("debug", "Entrada correcta")
                        }
                        .addOnFailureListener { _ ->
                            Log.e("debug", "Fallo al introducir datos")
                        }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.login_client_fail),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
    }

}

