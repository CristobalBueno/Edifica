package com.edifica.activities.clients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.edifica.R
import com.edifica.adapters.ClientAdsAdapter
import com.edifica.models.Ads
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practica.proyect_no_name.Interface.AdListener
import kotlinx.android.synthetic.main.activity_client_ads.*

class ActivityClientAds : AppCompatActivity(), AdListener {


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
        // TODO SANTANA
        // TODO NADA DE HARDCODES
        dialogBuilder.setMessage("¿Quieres eliminar el anuncio de ${Ad.settlement}?")
            .setPositiveButton("eliminar") { _, _ ->
                ads.remove(Ad)
                mAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        // TODO SANTANA
        // TODO NADA DE HARDCODES
        alert.setTitle("Eliminar anuncio")
        alert.show()
    }
}
