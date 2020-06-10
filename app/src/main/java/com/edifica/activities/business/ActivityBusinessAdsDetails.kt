package com.edifica.activities.business

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.models.Ads
import com.edifica.models.Dataholder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_business_ads_details.*
import kotlinx.android.synthetic.main.alert_dialog_offer.view.*

class ActivityBusinessAdsDetails : BaseActivity() {

    val ADV = "adv"

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

    fun updateView() {
        var currentAds: Ads = intent.getSerializableExtra(ADV) as Ads

        textView_name.text = currentAds.user.name
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

            .setPositiveButton(R.string.activity_business_ads_details_dialog_send_text) { dialog, id ->

                if (offer.text.toString().isNotEmpty()) {
                    Toast.makeText(
                        this,
                        getString(R.string.activity_business_ads_details_dialog_send),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    Dataholder.offersentbusiness = offer.text.toString()

                    Handler().postDelayed(Runnable {
                        gotoActivity(ActivityBusinessMain())
                    }, 1000)

                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.activity_business_ads_details_dialog_Nosend),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            }.setNegativeButton(R.string.activity_business_ads_details_dialog_cancel_text,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

        builder.setTitle(R.string.activity_business_ads_details_dialog_title)
        builder.setCancelable(false).create().show()
    }
}