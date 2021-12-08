package com.example.peguemonte.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.peguemonte.R
import com.example.peguemonte.entity.Client

class MyAdapter(
    val list:List<Client>): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_line, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        val client:Client = list.get(position)
        holder.textViewClientName.setText(client.name)
        holder.textViewClientPhone.setText(client.getPhone()?.number)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}