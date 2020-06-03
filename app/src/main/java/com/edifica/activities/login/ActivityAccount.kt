package com.edifica.activities.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.edifica.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_account.*
import java.io.File

class ActivityAccount : BaseActivity() {

    private var isValidEmail: Boolean = false
    private lateinit var token: Token

    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        button_count_login.setOnClickListener {
            emailVerification()
            success_Registration()
        }

        button_reset_password.setOnClickListener {
        }

        success_Registration()
    }

    private fun emailVerification() {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(text_count_email.text.toString())
                .matches() && !text_count_email.text.toString().isBlank()
        ) {
            isValidEmail = true
            text_count_email.error = null
        } else {
            isValidEmail = false
            text_count_email.error = getString(R.string.login_client_error_email)
        }
    }

    private fun success_Registration() {
        if (isValidEmail) {
            signUp(text_count_email.text.toString(), text_count_password.text.toString())
        }
    }

    private fun signUp(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val query = db.collection("users").document(auth.currentUser?.uid!!)
                    var dbUser: User?
                    token = Token()

                    query.get().addOnSuccessListener { document ->
                        dbUser = document.toObject(User::class.java)

                        token.name = dbUser?.name.toString()
                        token.phone = dbUser?.phone.toString()
                        token.identifier = dbUser?.identifier!!
                        token.email = dbUser?.email.toString()
                        token.uid = auth.currentUser?.uid ?: ""

                        token.saveToken(File(filesDir, Dataholder.FILENAME))

                        gotoActivity(ActivityAnimation())

                        Log.d("DATABASE", "success updating data")
                    }.addOnFailureListener { exception ->
                        Log.d("DATABASE", "get failed with", exception)
                    }
                } else {

                    Toast.makeText(
                        baseContext, R.string.activity_count_authentication_failed,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }
}

