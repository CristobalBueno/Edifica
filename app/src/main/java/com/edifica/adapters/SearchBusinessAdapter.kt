package com.edifica.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.models.User
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_business.view.*

class SearchBusinessAdapter(recyclerOptions: FirestoreRecyclerOptions<User>) : FirestoreRecyclerAdapter<User, SearchBusinessAdapter.MyViewHolder>(recyclerOptions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBusinessAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_business, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: User) {
        Log.d("DEBUG", model.toString())
        holder.show(model)
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