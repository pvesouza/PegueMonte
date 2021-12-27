package com.example.peguemonte.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.peguemonte.R
import com.example.peguemonte.entity.Client

class MyAdapter(
    private var list:List<Client>,
    private val  context: Context,
): RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var onItemClickListener:(Client)->Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_line, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        val client:Client = this.list[position]
        holder.bind(client, this.context)
        //Binds the click listener
        holder.itemView.setOnClickListener{this.onItemClickListener(client) }

    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    fun setList(list:List<Client>){
        this.list = list
    }

    fun setOnItemClickListener(listener: (Client) -> Unit){
        this.onItemClickListener = listener
    }

}

