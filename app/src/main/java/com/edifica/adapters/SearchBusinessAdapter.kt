package com.edifica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.models.Business
import com.practica.proyect_no_name.Interface.CustomItemListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_business.view.*

class SearchBusinessAdapter(private val data: List<Business>, private val onItemClick: CustomItemListener) : RecyclerView.Adapter<SearchBusinessAdapter.MyViewHolder>() {
    private var filtered: ArrayList<Business> = arrayListOf()
    init {
        filtered.addAll(data)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBusinessAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_business, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = data[position]

        data.let {
            holder.show(it)
            if (!filtered.isNullOrEmpty() && position <= filtered.size) {
                holder.show(filtered[position])
                holder.itemView.card_view.setOnClickListener {
                    onItemClick.onItemClick(filtered[position],position)
                }
            }else{
                holder.itemView.card_view.setOnClickListener {
                    onItemClick.onItemClick(data,position)
                }
            }
        }
    }

    fun filter(text: String) {

        if (text.isNotEmpty()) {
            filtered = data.filter { it.name.toUpperCase().contains(text.toUpperCase()) } as ArrayList<Business>

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
        private val textnamebusiness = view.findViewById(R.id.text_name_business) as TextView
        private val textemailbusiness = view.findViewById(R.id.text_email_business)as TextView
        private val imagebusiness = view.findViewById(R.id.image_business)as ImageView

        fun show(data: Business) {
            textnamebusiness.text = data.name
            textemailbusiness.text = data.email
            Picasso.get().load(data.image).into(imagebusiness)
        }
    }
}