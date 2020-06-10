package com.practica.proyect_no_name.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.models.Guild
import com.practica.proyect_no_name.Interface.GridCustomListener
import kotlinx.android.synthetic.main.item_client_grid.view.*

class GridAdapter(private val mDataSet: Array<Guild>?, val clickAction: GridCustomListener) :
    RecyclerView.Adapter<GridAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_client_grid, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = mDataSet?.get(position)

        data?.let {
            holder.bindItems(it)
        }

        holder.itemView.card_view.setOnClickListener() {
            val image_check_box = it.findViewById(R.id.grid_item_check_box) as ImageView
            val image_check = it.findViewById(R.id.grid_item_check) as ImageView

            data?.isChecked = !data?.isChecked!!

            if (!data.isChecked) {
                image_check_box.visibility = View.INVISIBLE
                image_check.visibility = View.INVISIBLE
            } else {
                image_check_box.visibility = View.VISIBLE
                image_check.visibility = View.VISIBLE
            }

            clickAction.onItemClick(data, position)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet?.size ?: 0
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val text_business = v.findViewById(R.id.grid_item_text) as TextView
        private val image_business = v.findViewById(R.id.grid_item_icon) as ImageView
        private val image_check_box = v.findViewById(R.id.grid_item_check_box) as ImageView
        private val image_check = v.findViewById(R.id.grid_item_check) as ImageView

        fun bindItems(data: Guild) {
            text_business.text = data.guildName
            image_business.setImageDrawable(data.drawable)

            if (!data.isChecked) {
                image_check_box.visibility = View.INVISIBLE
                image_check.visibility = View.INVISIBLE
            }
        }
    }
}