package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData

class CustomAdapterFavorites(private var data: ArrayList<DataClass.Favor>): RecyclerView.Adapter<CustomAdapterFavorites.ViewHolder>(){
    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {


        val title: TextView = itemView.findViewById(R.id.fvrTitle);
        val price: TextView = itemView.findViewById(R.id.fvrPrice);
        val image: ImageView = itemView.findViewById(R.id.fvrImage);

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
        for (i in  0 until  TempData.productArray.size ){ //step 1
            if(data[position].id_product == TempData.productArray[i].id){
                holder.price.text = TempData.productArray[i].price.toString();
                holder.title.text = TempData.productArray[i].title
                holder.image.setImageDrawable(TempData.productArray[i].image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_favourites, parent, false)

        return ViewHolder(view, View.OnClickListener {} )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}