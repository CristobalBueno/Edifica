package com.edifica.activities.clients

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.adapters.ClientAdsAdapter
import com.edifica.models.Ads
import com.edifica.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.practica.proyect_no_name.Interface.AdListener
import kotlinx.android.synthetic.main.activity_client_ads.*

class ActivityClientAds : BaseActivity(), AdListener {

    lateinit var mAdapter: ClientAdsAdapter
    var db = FirebaseFirestore.getInstance()
    var allAds: ArrayList<Ads> = arrayListOf()
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_ads)

        loadAdapter()
    }

    override fun onStart() {
        super.onStart()

        loadAllBusinessDataBase()

    }

    override fun deleteAd(Ad: Ads) {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)

        dialogBuilder.setMessage(R.string.client_ads_alert_dialog_message)
            .setPositiveButton(R.string.client_ads_alert_dialog_delete) { _, _ ->
                allAds.remove(Ad)
                mAdapter.notifyDataSetChanged()
            }
            .setNegativeButton(R.string.client_ads_alert_dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()

        alert.setTitle(R.string.client_ads_alert_dialog_title)
        alert.show()
    }

    fun loadAdapter() {
        mAdapter = ClientAdsAdapter(allAds, this)
        ads_RecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ads_RecyclerView.adapter = mAdapter
    }

    fun loadAllBusinessDataBase() {
        val query = db.collection("ads")

        query.get().addOnSuccessListener { documents ->
            if (documents != null) {
                for (document in documents) {
                    var user: User?
                    (document["user"] as DocumentReference).get().addOnSuccessListener {
                        user = it.toObject(User::class.java)

                        if (user?.uid == auth.currentUser?.uid) {
                            var ads = document.toObject(Ads::class.java)
                            ads.user = user!!
                            Log.d("Ads", ads.toString())
                            allAds.add(ads)
                            loadAdapter()
                        }
                    }

                }
            } else {
                Log.d("miapp", "no such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("miapp", "get failed with", exception)
        }
    }
}
