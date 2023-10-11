package com.example.coffeeshop_20

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import java.text.FieldPosition

class CustomAdapterMenu(private var data: List<DataClass.Coffee>): RecyclerView.Adapter<CustomAdapterMenu.ViewHolder>() {


    class ViewHolder(itemView: View,private val listener: OnClickListener): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var name: TextView = itemView.findViewById(R.id.name);
        var shorDescription: TextView = itemView.findViewById(R.id.short_description);
        var price: TextView = itemView.findViewById(R.id.price)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = data[position];
        holder.name.text = data[position].name;
        holder.shorDescription.text = data[position].description;


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_layout_menu,parent,false)
        return ViewHolder(view,OnClickListener{})
    }
    override fun getItemCount(): Int {
        return data.size
    }
}