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

    private var allBusiness: ArrayList<User> = arrayListOf()
    private var filtered: ArrayList<User> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_business, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: User) {

        model.let {
            if (!filtered.isNullOrEmpty() && position <= filtered.size) {
                allBusiness.add(model)
                Log.w("miapp", "HA ENTRADO")
                holder.show(filtered[position])
//                holder.itemView.card_view.setOnClickListener {
//                    onItemClick.onItemClick(filtered[position],position)
//                }
            }else{
                allBusiness.add(model)
                Log.w("miapp", "HA ENTRADO EN ELSE")
                holder.show(allBusiness[position])
//                holder.itemView.card_view.setOnClickListener {
//                    onItemClick.onItemClick(data,position)
//                }
            }

        }
    }

    fun filter(text: String) {

        if (text.isNotEmpty()) {
            Log.w("miapp", "SEARCH ALLBUSINESS --> $allBusiness")
            filtered = allBusiness.filter { it.name.toUpperCase().contains(text.toUpperCase()) } as ArrayList<User>
            Log.w("miapp", "SEARCH FILTERED --> $filtered")

            if (filtered.isNullOrEmpty()) {
                filtered.clear()
            }

        } else {
            filtered.clear()
            filtered.addAll(allBusiness)
        }
        notifyDataSetChanged()
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