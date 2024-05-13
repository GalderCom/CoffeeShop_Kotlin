package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.Fragments.FragmentMyData
import com.example.coffeeshop_20.Fragments.FragmentSignUp
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData

class CustomAdapterGender (private var data: ArrayList<DataClass.Gender>): RecyclerView.Adapter<CustomAdapterGender.ViewHolder>() {

    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.text_gender);

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
        holder.title.text = data[position].name;


        holder.title.setOnClickListener {
            try {
                FragmentMyData.textViewGender.text = data[position].name;
                FragmentMyData.mRecyclerGender.visibility = View.GONE;
                FragmentMyData.arrowGender.rotation = holder.itemView.rotation.plus(90)
                TempData.selectGender = data[position].id
            }
            catch (ex: Exception)
            {
                FragmentSignUp.textViewGender.text = data[position].name;
                FragmentSignUp.mRecyclerGender.visibility = View.GONE;
                FragmentSignUp.arrowGender.rotation = holder.itemView.rotation.plus(90)
                TempData.selectGender = data[position].id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_gender, parent, false)

        return ViewHolder(view, View.OnClickListener {} )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
