package com.example.coffeeshop_20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterBakery(private var data: ArrayList<DataClass.Bakery>): RecyclerView.Adapter<CustomAdapterBakery.ViewHolder>() {
    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name: TextView = itemView.findViewById(R.id.name);
        var weight: TextView = itemView.findViewById(R.id.short_description);
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
        holder.price.text = data[position].price.toString();
        holder.weight.text = data[position].weight;


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_menu, parent, false)
        return ViewHolder(view, View.OnClickListener {})
    }

    override fun getItemCount(): Int {
        return data.size
    }
}