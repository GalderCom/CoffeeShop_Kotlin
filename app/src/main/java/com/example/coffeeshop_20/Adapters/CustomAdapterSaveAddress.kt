package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.Fragments.FragmentAddAddress
import com.example.coffeeshop_20.R



class CustomAdapterSaveAddress (private var data: ArrayList<DataClass.SaveAddress>): RecyclerView.Adapter<CustomAdapterSaveAddress.ViewHolder>() {


    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.name_address);
        val address: TextView = itemView.findViewById(R.id.addressText)
        val btnEdit: ImageView = itemView.findViewById(R.id.edit_address)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = data[position];
        holder.name.text = data[position].name;

        val address = data[position].street + ", д " + data[position].house

        holder.address.text = address

        holder.btnEdit.setOnClickListener(){

            val fragmentManager: androidx.fragment.app.FragmentManager = (holder.itemView.context as ActivityMain).supportFragmentManager
            val fragment = FragmentAddAddress()
            val bundle = Bundle()

            bundle.putInt("intValue", data[position].id) // intValue замените на вашу переменную
            fragment.arguments = bundle

            fragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                fragment,"Add_Address").commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_save_address, parent, false)

        return ViewHolder(view, View.OnClickListener {})
    }

    override fun getItemCount(): Int {
        return data.size
    }
}