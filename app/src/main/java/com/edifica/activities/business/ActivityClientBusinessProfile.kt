package com.edifica.activities.business

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.view.View
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.edifica.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_client_business_profile.*
import kotlinx.android.synthetic.main.alert_dialog_rating.view.*
import java.io.File

class ActivityClientBusinessProfile : BaseActivity() {

    lateinit var currentBusiness: User
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_business_profile)

        currentBusiness = intent.getSerializableExtra(OBJECT) as User

        updateViewProfile()

        button_rating.setOnClickListener {
            alertDialog()
        }

        button_web.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(currentBusiness.web)))
        }
    }

    fun updateViewProfile() {
        if (currentBusiness.image.isNotEmpty()) {
            Picasso.get().load(currentBusiness.image).into(image_business_profile)
        }
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
        var rating_media = 0F
        var send_rating: ArrayList<Float> = arrayListOf()

        ratings.add(0, dialogView.input_rating_communication.rating)
        ratings.add(1, dialogView.input_rating_cleaning.rating)
        ratings.add(2, dialogView.input_rating_deadlines.rating)
        ratings.add(3, dialogView.input_rating_quality.rating)
        ratings.add(4, dialogView.input_rating_professionalism.rating)

        rating_media = ratings.sum() / 5
        send_rating.add(rating_media)
        ratings.add(0, rating_media)

        ratings.forEach {
            send_rating.add(it)
        }

        ratingCalculation(ratings)
        Log.w("miapp", currentBusiness.toString())
        updateViewProfile()

        val query = db.collection("users").document(currentBusiness.uid)
        query.set(currentBusiness)

        Log.w("miapp", "Valor obtenido ${ratings}")
        Log.w("miapp", "valor suma total: ${rating_media}")
    }

    fun ratingCalculation(ratingAdded: ArrayList<Float>) {
        var finalRating: ArrayList<Float> = arrayListOf()

        if (currentBusiness.ratingsCount > 0) {
            for ((index, value) in ratingAdded.withIndex()) {
                val number = (((currentBusiness.ratings[index] * currentBusiness.ratingsCount) + value))
                Log.w("miapp", "ENVIO: ${number}")
                finalRating.add(number.div(currentBusiness.ratingsCount + 1))
            }
            currentBusiness.ratings.clear()
            currentBusiness.ratings.addAll(finalRating)
            currentBusiness.ratingsCount += 1
        } else {
            currentBusiness.ratings.clear()
            currentBusiness.ratings.addAll(ratingAdded)
            currentBusiness.ratingsCount += 1
        }
    }

}
