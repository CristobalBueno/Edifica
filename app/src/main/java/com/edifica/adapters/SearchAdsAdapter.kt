package com.edifica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.interfaces.CustomAdsListener
import com.edifica.models.Ads
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_ads.view.*

class SearchAdsAdapter (private val data: List<Ads>, private val onItemAdsClick: CustomAdsListener) : RecyclerView.Adapter<SearchAdsAdapter.MyViewHolder>() {
    private var filtered: ArrayList<Ads> = arrayListOf()

    init {
        filtered.addAll(data)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_ads, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = data.get(position)

        data.let {
            holder.show(it)
            if (!filtered.isNullOrEmpty() && position <= filtered.size) {
                holder.show(filtered[position])
                holder.itemView.card_view.setOnClickListener {
                    onItemAdsClick.onItemAdsClick(filtered[position],position)
                }
            }else{
                holder.itemView.card_view.setOnClickListener {
                    onItemAdsClick.onItemAdsClick(data,position)
                }
            }
        }
    }

    fun filter(text: String) {

        if (text.isNotEmpty()) {
            filtered = data.filter { it.settlement.toUpperCase().contains(text.toUpperCase()) } as ArrayList<Ads>

            if (filtered.isNullOrEmpty()) {
                filtered.clear()
            }
        } else {
            filtered.clear()
            filtered.addAll(data)
        }
        notifyDataSetChanged()
    }
    override fun getItemCount() = filtered.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textNameAds = view.findViewById(R.id.text_name_ads) as TextView
        private val textSettlementAds = view.findViewById(R.id.text_settlement_ads)as TextView
        private val textProvinceAds = view.findViewById(R.id.text_province_ads)as TextView
        private val imageAds = view.findViewById(R.id.image_ads)as ImageView

        fun show(data: Ads) {
            textNameAds.text = data.user.name
            textSettlementAds.text = data.settlement
            textProvinceAds.text = data.province
            Picasso.get().load(data.images[0]).into(imageAds)
        }
    }
}