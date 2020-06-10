package com.edifica.activities.business

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.activities.login.ActivityAnimation
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_business_profile_mod.*
import kotlinx.android.synthetic.main.alert_dialog_profile_mod.view.*
import java.io.File

class ActivityBusinessProfileMod : BaseActivity() {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()
    val TAG = "debug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_profile_mod)

        setDefaultLayout()

        business_profile_mod_photo.setOnClickListener { changePhoto() }
        business_profile_mod_name.setOnClickListener { changeName() }
        business_profile_mod_email.setOnClickListener { changeEmail() }
        business_profile_mod_web.setOnClickListener { changeWeb() }
        business_profile_mod_delete_User.setOnClickListener { deleteUser() }
    }

    private fun setDefaultLayout() {
        business_profile_mod_photo.setText(R.string.activity_business_profile_budgets_mod_business_profile_mod_photo)
        business_profile_mod_name.setText(R.string.activity_business_profile_budgets_mod_business_profile_mod_name)
        business_profile_mod_email.setText(R.string.activity_business_profile_budgets_mod_business_profile_mod_email)
        business_profile_mod_web.setText(R.string.activity_business_profile_budgets_mod_business_profile_mod_web)
        business_profile_mod_delete_User.setText(R.string.activity_business_profile_budgets_mod_business_profile_mod_delete_User)
    }

    private fun changePhoto() {


        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_DENIED
        ) {

            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_CODE)
        } else {
            pickImageFromGallery()
        }

    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(
                        this,
                        R.string.activity_business_profile_budgets_mod_business_profile_mod_denied_permission_photo,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //TODO actualizar base de datos server.photo = data?.data!!
        }
    }

    @SuppressLint("InflateParams")
    private fun changeName() {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod, null)
        val name = dialogView.editText_alert_dialog
        name.setHint(R.string.activity_business_profile_budgets_mod_business_profile_mod_hint_change_name)

        dialogBuilder.setView(dialogView)
            .setPositiveButton(R.string.activity_client_profile_mod_affirmative_question_change_name) { dialog, _ ->
                //TODO actualizar de base datos server.name = name.text.toString()
            }
            .setNegativeButton(R.string.activity_business_profile_budgets_mod_business_profile_mod_negative_change_name) { dialog, _ ->
                dialog.cancel()
            }

        dialogBuilder.setTitle(R.string.activity_business_profile_budgets_mod_business_profile_mod_question_change_name)
        dialogBuilder.setCancelable(false).create().show()
    }

    @SuppressLint("InflateParams")
    private fun changeEmail() {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod, null)
        val name = dialogView.editText_alert_dialog
        name.setHint(R.string.activity_business_profile_budgets_mod_business_profile_mod_hint_change_email)

        dialogBuilder.setView(dialogView)
            .setPositiveButton(R.string.activity_business_profile_budgets_mod_business_profile_mod_affirmative_change_email) { dialog, _ ->
                //TODO actualizar de base de datoss server.email = name.text.toString()
            }
            .setNegativeButton(R.string.activity_business_profile_budgets_mod_business_profile_mod_negative_change_email) { dialog, _ ->
                dialog.cancel()
            }

        dialogBuilder.setTitle(R.string.activity_business_profile_budgets_mod_business_profile_mod_question_change_email)
        dialogBuilder.setCancelable(false).create().show()
    }

    @SuppressLint("InflateParams")
    private fun changeWeb() {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod, null)
        val name = dialogView.editText_alert_dialog
        name.setHint(R.string.activity_business_profile_budgets_mod_business_profile_mod_hint_change_web)

        dialogBuilder.setView(dialogView)
            .setPositiveButton(R.string.activity_business_profile_budgets_mod_business_profile_mod_affirmative_change_web) { dialog, _ ->
                //TODO actualizar de base de datoss server.email = name.text.toString()
            }
            .setNegativeButton(R.string.activity_business_profile_budgets_mod_business_profile_mod_negative_change_web) { dialog, _ ->
                dialog.cancel()
            }

        dialogBuilder.setTitle(R.string.activity_business_profile_budgets_mod_business_profile_mod_question_change_web)
        dialogBuilder.setCancelable(false).create().show()

    }

    private fun deleteUser() {

        Token.deleteToken(File(filesDir, Dataholder.FILENAME))

        auth.currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }

        gotoActivity(ActivityAnimation())
        finish()
    }

}
