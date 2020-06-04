package com.edifica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.models.User
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.practica.proyect_no_name.Interface.CustomItemListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_business.view.*

class SearchBusinessAdapter(recyclerOptions: FirestoreRecyclerOptions<User>, var customItemListener: CustomItemListener) :
    FirestoreRecyclerAdapter<User, SearchBusinessAdapter.MyViewHolder>(recyclerOptions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_business, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: User) {
        holder.show(model)
        holder.itemView.card_view.setOnClickListener {
            customItemListener.onItemClick(model, position)
        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun show(data: User) {
            itemView.text_name_business.text = data.name
            itemView.text_email_business.text = data.email
            if (data.image.isNotEmpty()) {
                Picasso.get().load(data.image).into(itemView.image_business)
            }
        }

    }
}