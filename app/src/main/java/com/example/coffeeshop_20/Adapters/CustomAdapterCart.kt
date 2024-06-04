package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.Fragments.FragmentCart
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData

class CustomAdapterCart (private var data: ArrayList<DataClass.Cart>): RecyclerView.Adapter<CustomAdapterCart.ViewHolder>() {


    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val title: TextView = itemView.findViewById(R.id.cart_name);
        val image: ImageView = itemView.findViewById(R.id.cart_image)
        val desc :TextView = itemView.findViewById(R.id.cart_desc)
        val price: TextView = itemView.findViewById(R.id.cart_price)
        val count: TextView = itemView.findViewById(R.id.cart_count)
        val btnPlus: ImageButton = itemView.findViewById(R.id.btnPlus)
        val btnMinus: ImageButton = itemView.findViewById(R.id.btnMinus)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v)
        }

    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = data[position];


        holder.count.text = data[position].count_.toString()
        for (i in 0 until TempData.productArray.size)
        {
            if(data[position].id_product == TempData.productArray[i].id){
                holder.price.text = TempData.productArray[i].price.toString()
                holder.desc.text = TempData.productArray[i].description
                holder.title.text = TempData.productArray[i].title
                holder.desc.text = TempData.productArray[i].weight
                holder.image.setImageDrawable(TempData.productArray[i].image)
                break
            }
        }

        holder.btnPlus.setOnClickListener(){
            if(holder.count.text.toString().toInt() < 5)
            {
                TempData.newCart[position].count_ ++
                holder.count.text = TempData.newCart[position].count_.toString()
                FragmentCart.customAdapterCart.notifyDataSetChanged()
            }
        }

        holder.btnMinus.setOnClickListener(){
            if(holder.count.text.toString().toInt() > 1){
                TempData.newCart[position].count_ --
                holder.count.text = TempData.newCart[position].count_.toString()
                FragmentCart.customAdapterCart.notifyDataSetChanged()
            }
            else if(holder.count.text.toString().toInt() == 1)
            {
                TempData.newCart.remove(TempData.newCart[position])
                notifyDataSetChanged()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_cart, parent, false)

        return ViewHolder(view, View.OnClickListener {} )
    }

    override fun getItemCount(): Int {
        return data.size
    }

}