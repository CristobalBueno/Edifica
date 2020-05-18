package com.edifica.activities.business

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.models.Business
import com.edifica.models.Dataholder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_client_business_profile.*
import kotlinx.android.synthetic.main.alert_dialog_rating.view.*

class ActivityClientBusinessProfile : BaseActivity() {

    lateinit var currentBusiness: Business

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_business_profile)

        currentBusiness = intent.getSerializableExtra(OBJECT) as Business

        updateViewProfile()

        button_rating.setOnClickListener {
            alertDialog()
        }

        button_web.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(currentBusiness.web)))
        }
    }

    fun updateViewProfile() {
        Picasso.get().load(currentBusiness.image).into(image_business_profile)
        text_name_business.text = currentBusiness.name
        text_phone_business.text = currentBusiness.phone
        text_email_business.text = currentBusiness.email
        view_rating_general.rating = currentBusiness.ratings[0]
        view_rating_communication.rating = currentBusiness.ratings[1]
        view_rating_cleaning.rating = currentBusiness.ratings[2]
        view_rating_deadlines.rating = currentBusiness.ratings[3]
        view_rating_quality.rating = currentBusiness.ratings[4]
        view_rating_professionalism.rating = currentBusiness.ratings[5]
    }

    fun alertDialog() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.alert_dialog_rating, null)

        builder.setView(dialogView)

            .setPositiveButton(R.string.business_profile_alert_dialog_rating_send) { _, _ ->

                //TODO falta añadir si puede hacer la valoracion if()
                logic_Ratings(dialogView)
            }

            .setNegativeButton(R.string.business_profile_alert_dialog_rating_cancel,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                })

        builder.setTitle(R.string.business_profile_alert_dialog_rating_title)
        builder.setCancelable(false).create().show()
    }

    fun logic_Ratings(dialogView: View) {
        var ratings: ArrayList<Float> = arrayListOf()
        var rating_media: Float
        var send_rating: ArrayList<Float> = arrayListOf()

        ratings.add(0, dialogView.input_rating_communication.rating)
        ratings.add(1, dialogView.input_rating_cleaning.rating)
        ratings.add(2, dialogView.input_rating_deadlines.rating)
        ratings.add(3, dialogView.input_rating_quality.rating)
        ratings.add(4, dialogView.input_rating_professionalism.rating)

        rating_media = ratings.sum() / 5
        send_rating.add(rating_media)

        ratings.forEach {
            send_rating.add(it)
        }

        //TODO mandar los datos al servido, dentro de la empresa
        Dataholder.inputRatingBusiness = send_rating

        Log.w("miapp", "Valor obtenido ${ratings}")
        Log.w("miapp", "valor suma total: ${rating_media}")

        Log.w("miapp", "ENVIO: ${Dataholder.inputRatingBusiness}")
    }
}
