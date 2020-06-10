package com.edifica.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.models.Chat

class ChatAdapter(private val mDataSet: Chat?) :
    RecyclerView.Adapter<ChatAdapter.MainViewHolder>() {

    val USERTEXT = "text"
    val TIMESTAMP = "timestamp"
    val USERUID = "user"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return MainViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mDataSet?.messages?.size ?: 0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = mDataSet?.messages?.get(position)

        data?.let {
            holder.bindItems(it)
        }
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val user_name = v.findViewById(R.id.chat_text_name) as TextView
        private val user_text = v.findViewById(R.id.chat_text) as TextView

        fun bindItems(data: Map<String, String>) {
            var uid = data[USERUID].toString()

            if (mDataSet?.user?.uid == uid) {
                user_name.text = mDataSet?.user?.name + " dice: "
            } else if (mDataSet?.business?.uid == uid) {
                user_name.text = mDataSet?.business?.name + " dice: "
            }

            user_text.text = data[USERTEXT].toString()
        }
    }
}