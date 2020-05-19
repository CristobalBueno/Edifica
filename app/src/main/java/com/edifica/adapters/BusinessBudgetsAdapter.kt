package com.edifica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.models.Ads
import com.practica.proyect_no_name.Interface.AdListener
import kotlinx.android.synthetic.main.item_budgets.view.*

class BusinessBudgetsAdapter (
    private val mDataSet: List<Ads>?,
    private val adListener: AdListener) : RecyclerView.Adapter<BusinessBudgetsAdapter.MainViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_budgets, parent, false)
            return MainViewHolder(v)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val data = mDataSet?.get(position)
            data?.let {
                    workSelected ->
                holder.bindItems(workSelected)

                holder.itemView.item_budgets.setOnLongClickListener {
                    adListener.deleteAd(workSelected)
                    true
                }
            }
        }

        override fun getItemCount(): Int {
            return mDataSet?.size ?: 0
        }

        inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            private val clientName = v.findViewById(R.id.budget_client_name) as TextView
            private val settlement = v.findViewById(R.id.budget_settlement) as TextView
            private val province = v.findViewById(R.id.budget_province) as TextView
            private val fInfo = v.findViewById(R.id.budget_fInfo) as TextView
            private val price = v.findViewById(R.id.budget_price) as TextView


            fun bindItems(data: Ads) {
                clientName.text = data.user.name
                settlement.text = data.settlement
                province.text = data.province
                fInfo.text = data.formInfo
                //TODO implementar coojiendo el nombre de la empresa
                //price.text = data.usersBusiness.find{user -> user === userBusiness.name}.price
            }
        }
    }