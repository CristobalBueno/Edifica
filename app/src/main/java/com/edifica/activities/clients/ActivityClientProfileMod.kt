package com.edifica.activities.clients

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.activities.login.ActivityAnimation
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.edifica.models.Token.Companion.readToken
import com.edifica.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_client_profile_mod.*
import kotlinx.android.synthetic.main.alert_dialog_profile_mod.view.*
import java.io.File

class ActivityClientProfileMod : BaseActivity() {

    var db = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_profile_mod)

        setDefaultLayout()

        client_profile_mod_name.setOnClickListener { changeName() }
        client_profile_mod_email.setOnClickListener { changeEmail() }
        client_profile_delete_User.setOnClickListener {
            try {
                Token.deleteToken(File(filesDir, Dataholder.FILENAME))
                db.collection("users").document(auth.currentUser?.uid!!).delete()

                auth.currentUser?.delete()
                auth.signOut()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            gotoActivity(ActivityAnimation())
            finish()
        }
    }

    private fun setDefaultLayout() {
        client_profile_mod_name.setText(R.string.activity_client_profile_mod_change_name_button)
        client_profile_mod_email.setText(R.string.activity_client_profile_mod_change_email_button)
        client_profile_delete_User.setText(R.string.activity_client_profile_mod_delete_User)

    }

    private fun changeName() {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod, null)
        val name = dialogView.editText_alert_dialog
        name.setHint(R.string.activity_client_profile_mod_hint_question_change_name)

        dialogBuilder.setView(dialogView)

            .setPositiveButton(R.string.activity_client_profile_mod_affirmative_question_change_name) { dialog, _ ->
                db.collection("users").document(auth.currentUser?.uid!!)
                    .update("name", name.text.toString())
            }
            .setNegativeButton(R.string.activity_client_profile_mod_negative_question_change_name) { dialog, _ ->
                dialog.cancel()
            }


        dialogBuilder.setTitle(R.string.activity_client_profile_mod_question_change_name)
        dialogBuilder.setCancelable(false).create().show()
    }

    override fun onPause() {
        super.onPause()

        var userToken = readToken(File(filesDir, Dataholder.FILENAME))

        val query = db.collection("users").document(auth.currentUser?.uid.toString())
        query.get().addOnSuccessListener { document ->
            if (document != null) {
                var myUser = document.toObject(User::class.java)
                if (myUser != null) {
                    userToken.name= myUser.name
                    userToken.phone = myUser.phone
                    userToken.email = myUser.email
                    userToken.saveToken(File(filesDir , Dataholder.FILENAME))
                }
            } else {
                Log.d("miapp", "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("miapp", "get failed with ", exception)

        }
    }

    private fun changeEmail() {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod, null)
        val name = dialogView.editText_alert_dialog
        name.setHint(R.string.activity_client_profile_mod_hint_question_change_email)

        dialogBuilder.setView(dialogView)

            .setPositiveButton(R.string.activity_client_profile_mod_affirmative_question_change_email) { dialog, _ ->

                auth.currentUser?.updateEmail(name.text.toString())?.addOnCompleteListener {
                    db.collection("users").document(auth.currentUser?.uid!!)
                        .update("email", name.text.toString())
                }
            }
            .setNegativeButton(R.string.activity_client_profile_mod_negative_question_change_email) { dialog, _ ->
                dialog.cancel()
            }

        dialogBuilder.setTitle(R.string.activity_client_profile_mod_question_change_email)
        dialogBuilder.setCancelable(false).create().show()
    }
}
