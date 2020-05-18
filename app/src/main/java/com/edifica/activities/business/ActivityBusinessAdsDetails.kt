package com.edifica.activities.business

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.models.Dataholder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_business_ads_details.*
import kotlinx.android.synthetic.main.alert_dialog_offer.view.*

class ActivityBusinessAdsDetails : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_ads_details)

        updateView()

        button_back.setOnClickListener {
            gotoActivity(ActivityBusinessMain())
        }

        button_accept.setOnClickListener {
            alertDialog()
        }

    }

    fun updateView(){
        // TODO SANTANA
        // TODO NADA DE DATAHOLDER, TODO O MEMORIA INTERNA O BASE DE DATOS
        val currentAds = Dataholder.currentAds

        textView_name.text = currentAds.userClient.name
        textView_settlement.text = currentAds.settlement
        textView_province.text = currentAds.province

        textView_formulary.text = currentAds.formInfo

        Picasso.get().load(currentAds.images[0]).into(imageView_photos)
    }

    fun alertDialog() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_offer, null)
        val offer = dialogView.editText_alertdialog_offer

        builder.setView(dialogView)
            // TODO SANTANA
            // TODO NADA DE HARDCODES
            .setPositiveButton("Enviar"){ dialog, id ->

                //TODO falta añadir el aviso al cliente y la cuantía

                if (offer.text.toString().isNotEmpty()){
                    Toast.makeText(this, getString(R.string.activity_business_ads_details_dialog_send), Toast.LENGTH_LONG)
                        .show()
                    Dataholder.offersentbusiness = offer.text.toString()

                    Handler().postDelayed(Runnable {
                        gotoActivity(ActivityBusinessMain())
                    }, 1000)

                }else{
                    Toast.makeText(this, getString(R.string.activity_business_ads_details_dialog_Nosend), Toast.LENGTH_LONG)
                        .show()
                }

            }

            // TODO SANTANA
            // TODO NADA DE HARDCODES
            .setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

        // TODO SANTANA
        // TODO NADA DE HARDCODES
        builder.setTitle("Envia una cuatía aproximada")
        builder.setCancelable(false).create().show()
    }
}