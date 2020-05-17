package com.edifica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.models.Ads
import com.practica.proyect_no_name.Interface.AdListener
import kotlinx.android.synthetic.main.item_ad.view.*

class ClientAdsAdapter   (private val mDataSet: List<Ads>?,
                          private val adListener: AdListener
) :
RecyclerView.Adapter<ClientAdsAdapter.MainViewHolder>() {

    // TODO SANTANA
    // TODO Terminar la estetica de los ADS
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = mDataSet?.get(position)
        data?.let {

                adSelected -> holder.bindItems(adSelected)

            holder.itemView.item_ad.setOnLongClickListener {
                adListener.deleteAd(adSelected)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return mDataSet?.size ?: 0
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val text1 = v.findViewById(R.id.ad_settlement) as TextView
        private val text2 = v.findViewById(R.id.ad_province) as TextView
        private val text3 = v.findViewById(R.id.ad_fInfo) as TextView
        fun bindItems(data: Ads) {
            text1.text = data.settlement
            text2.text = data.province
            text3.text = data.formularyInfo
        }
    }
}