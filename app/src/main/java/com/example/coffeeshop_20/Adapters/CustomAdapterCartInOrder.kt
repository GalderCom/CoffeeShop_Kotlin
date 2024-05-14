package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData

class CustomAdapterCartInOrder  (private var data: ArrayList<DataClass.Cart>): RecyclerView.Adapter<CustomAdapterCartInOrder.ViewHolder>() {

    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name :TextView = itemView.findViewById(R.id.name)
        val count : TextView = itemView.findViewById(R.id.count)
        val price: TextView = itemView.findViewById(R.id.price)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = data[position];



        holder.count.text = data[position].count.toString()

        for (i in 0 until TempData.productArray.size)
        {
            if(data[position].id_product == TempData.productArray[i].id)
            {
                holder.name.text = TempData.productArray[i].title
                holder.price.text = (TempData.productArray[i].price * data[position].count).toString()
                break
            }
        }





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_cart_order, parent, false)

        return ViewHolder(view, View.OnClickListener {} )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}