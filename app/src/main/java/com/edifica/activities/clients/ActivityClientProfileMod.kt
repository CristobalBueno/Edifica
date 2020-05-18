package com.edifica.activities.clients

import android.annotation.SuppressLint
import android.os.Bundle
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.activities.login.ActivityAnimation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_client_profile_mod.*
import kotlinx.android.synthetic.main.alert_dialog_profile_mod.view.*

class ActivityClientProfileMod : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_profile_mod)

        setDefaultLayout()

        client_profile_mod_name.setOnClickListener { changeName() }
        client_profile_mod_email.setOnClickListener { changeEmail() }
        client_profile_delete_User.setOnClickListener {
            //TODO  delete user
            gotoActivity(ActivityAnimation())
            finish()
        }
    }

    private fun setDefaultLayout() {
        client_profile_mod_name.setText(R.string.activity_client_profile_mod_change_name_button)
        client_profile_mod_email.setText(R.string.activity_client_profile_mod_change_email_button)
        client_profile_delete_User.setText(R.string.activity_client_profile_mod_delete_User)

    }

    @SuppressLint("InflateParams")
    private fun changeName() {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod, null)
        val name = dialogView.editText_alert_dialog
        name.setHint(R.string.activity_client_profile_mod_hint_question_change_name)

        dialogBuilder.setView(dialogView)

            .setPositiveButton(R.string.activity_client_profile_mod_affirmative_question_change_name) { dialog, _ ->

                //TODO server.name = name.text.toString()
            }
            .setNegativeButton(R.string.activity_client_profile_mod_negative_question_change_name) { dialog, _ ->
                dialog.cancel()
            }


        dialogBuilder.setTitle(R.string.activity_client_profile_mod_question_change_name)
        dialogBuilder.setCancelable(false).create().show()
    }

    @SuppressLint("InflateParams")
    private fun changeEmail() {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod, null)
        val name = dialogView.editText_alert_dialog
        name.setHint(R.string.activity_client_profile_mod_hint_question_change_email)


        dialogBuilder.setView(dialogView)

            .setPositiveButton(R.string.activity_client_profile_mod_affirmative_question_change_email) { dialog, _ ->

                //TODO server.email = name.text.toString()
            }

            .setNegativeButton(R.string.activity_client_profile_mod_negative_question_change_email) { dialog, _ ->
                dialog.cancel()
            }

        dialogBuilder.setTitle(R.string.activity_client_profile_mod_question_change_email)
        dialogBuilder.setCancelable(false).create().show()
    }
}
