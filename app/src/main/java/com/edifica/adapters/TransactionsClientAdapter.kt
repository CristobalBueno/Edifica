package com.edifica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.interfaces.TransactionListener
import com.edifica.models.Transactions
import kotlinx.android.synthetic.main.item_transaction_client.view.*

class TransactionsClientAdapter(
    private val mDataSet: List<Transactions>?,
    private val transactionListener: TransactionListener
) :
    RecyclerView.Adapter<TransactionsClientAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_client, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = mDataSet?.get(position)
        data?.let { transactionListener ->
            holder.bindItems(transactionListener)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet?.size ?: 0
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val userBusiness =
            v.findViewById(R.id.user_business_item_transaction_client) as TextView
        private val settlement = v.findViewById(R.id.settlement_item_transaction_client) as TextView
        private val province = v.findViewById(R.id.province_item_transaction_client) as TextView
        private val price = v.findViewById(R.id.price_item_transaction_client) as TextView

        fun bindItems(data: Transactions) {
            userBusiness.text = data.userBusiness.name
            settlement.text = data.ad.settlement
            province.text = data.ad.province
            price.text = data.price.toString()
        }
    }
}
