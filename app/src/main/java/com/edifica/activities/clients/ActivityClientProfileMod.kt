package com.edifica.activities.clients

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.edifica.R
import com.edifica.models.Dataholder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_client_profile_mod.*
import kotlinx.android.synthetic.main.alert_dialog_profile_mod_email.view.*
import kotlinx.android.synthetic.main.alert_dialog_profile_mod_name.view.*

class ActivityClientProfileMod : AppCompatActivity() {

    // TODO SANTANA
    // TODO LO REFERENTE AL DATAHOLDER LO QUIERO QUITADO
    // TODO SI TE HACEN FALTA DATOS, LOS COGES DE LA MEMORIA INTERNA (preferiblemente)
    // TODO O LO COGES DE BASE DE DATOS (SI NO HAY NADA GUARDADO EN LA MEMORIA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_profile_mod)

        client_profile_mod_photo.setOnClickListener { changePhoto() }
        client_profile_mod_name.setOnClickListener {  changeName() }
        client_profile_mod_email.setOnClickListener { changeEmail() }
    }

    private fun changePhoto() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup to request runtime permission
                requestPermissions(permissions,
                    PERMISSION_CODE
                )
            }
            else{
                //permission already granted
                pickImageFromGallery()
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery()
        }
    }
    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //Permission code
        private val PERMISSION_CODE = 1001
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        // TODO SANTANA
        // TODO No se que es eso, pero no me gusta
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    // TODO SANTANA
                    // TODO NADA DE HARDCODES EN CODIGO, esto deberia recogerse de string
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            // TODO SANTANA
            // TODO ARREGLA ESTO (NADA DE DATAHOLDERS)
            Dataholder.photo = data?.data!!

            Log.v("miapp","data uri "+ data.data)
        }
    }



    private fun changeName () {
        val dialogBuilder = MaterialAlertDialogBuilder(this , R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod_name, null)
        val name = dialogView.editText_alertdialog_name

        dialogBuilder.setView(dialogView)
            // TODO SANTANA
            // TODO NADA DE HARDCODES EN CODIGO
            .setPositiveButton("Si") { dialog, _ ->
                // TODO SANTANA
                // TODO ARREGLA ESTO (NADA DE DATAHOLDERS)
                Dataholder.name = name.text.toString()
            }
            // TODO SANTANA
            // TODO NADA DE HARDCODES EN CODIGO
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }

        // TODO SANTANA
        // TODO NADA DE HARDCODES EN CODIGO
        dialogBuilder.setTitle("Desea cambiar el nombre")
        dialogBuilder.setCancelable(false).create().show()
    }

    private fun changeEmail () {
        val dialogBuilder = MaterialAlertDialogBuilder(this , R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_profile_mod_email, null)
        val name = dialogView.editText_alertdialog_email

        dialogBuilder.setView(dialogView)
            // TODO SANTANA
            // TODO NADA DE HARDCODES EN CODIGO
            .setPositiveButton("Si") { dialog, _ ->
                // TODO SANTANA
                // TODO ARREGLA ESTO (NADA DE DATAHOLDERS)
                Dataholder.email = name.text.toString()
            }
            // TODO SANTANA
            // TODO NADA DE HARDCODES EN CODIGO
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }

        // TODO SANTANA
        // TODO NADA DE HARDCODES EN CODIGO
        dialogBuilder.setTitle("Desea cambiar el email")
        dialogBuilder.setCancelable(false).create().show()
    }
}
