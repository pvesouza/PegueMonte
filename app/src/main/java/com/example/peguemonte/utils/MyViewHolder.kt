package com.example.peguemonte.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peguemonte.R
import com.example.peguemonte.entity.Client

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var textViewClientName: TextView = itemView.findViewById(R.id.clientname_textview)
    var textViewClientPhone: TextView = itemView.findViewById(R.id.clientphone_textview)


    fun bind(client:Client, context: Context){
        this.textViewClientPhone.text = client.getPhone()?.number ?: context.getString(R.string.no_phone)
        this.textViewClientName.text = client.name
    }
}