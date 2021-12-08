package com.example.peguemonte.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peguemonte.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var textViewClientName: TextView = itemView.findViewById(R.id.clientname_textview)
    var textViewClientPhone: TextView = itemView.findViewById(R.id.clientphone_textview)

}