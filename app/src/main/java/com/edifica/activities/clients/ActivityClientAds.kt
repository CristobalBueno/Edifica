package com.edifica.activities.clients

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.adapters.ClientAdsAdapter
import com.edifica.models.Ads
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practica.proyect_no_name.Interface.AdListener
import kotlinx.android.synthetic.main.activity_client_ads.*

class ActivityClientAds : BaseActivity(), AdListener {


    lateinit var mAdapter: ClientAdsAdapter
    var ads : ArrayList<Ads> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_ads)

        mAdapter = ClientAdsAdapter(ads, this)
        ads_RecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        ads_RecyclerView.adapter = mAdapter

    }

    override fun deleteAd (Ad: Ads) {
        val dialogBuilder = MaterialAlertDialogBuilder(this , R.style.MyMaterialAlertDialog)

        dialogBuilder.setMessage(R.string.client_ads_alert_dialog_message)
            .setPositiveButton(R.string.client_ads_alert_dialog_delete) { _, _ ->
                ads.remove(Ad)
                mAdapter.notifyDataSetChanged()
            }
            .setNegativeButton(R.string.client_ads_alert_dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()

        alert.setTitle(R.string.client_ads_alert_dialog_title)
        alert.show()
    }
}
