package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.Fragments.FragmentCart
import com.example.coffeeshop_20.Fragments.FragmentMyData
import com.example.coffeeshop_20.Fragments.FragmentSignUp
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData

class CustomAdapterAddressSelect (private var data: ArrayList<DataClass.SaveAddress>): RecyclerView.Adapter<CustomAdapterAddressSelect.ViewHolder>() {

    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.name_address);
        val address: TextView = itemView.findViewById(R.id.addressText)

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
        holder.name.text = data[position].name;

        val address = data[position].street + ", д " + data[position].house

        holder.address.text = address


        holder.itemView.setOnClickListener {
            try {
                FragmentCart.textAddress.text = data[position].name;
                FragmentCart.mRecyclerViewAddress.visibility = View.GONE;
                FragmentCart.imageArrow.rotation = holder.itemView.rotation.plus(90)
                TempData.selectGender = data[position].id
            }
            catch (ex: Exception)
            {
                FragmentCart.textAddress.text = data[position].name;
                FragmentCart.mRecyclerViewAddress.visibility = View.GONE;
                FragmentCart.imageArrow.rotation  = holder.itemView.rotation.plus(90)
                TempData.selectGender = data[position].id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_addres_select, parent, false)

        return ViewHolder(view, View.OnClickListener {} )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}