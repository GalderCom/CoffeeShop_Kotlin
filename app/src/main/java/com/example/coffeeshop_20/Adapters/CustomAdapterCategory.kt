package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CustomAdapterCategory (private var data: ArrayList<DataClass.Category>): RecyclerView.Adapter<CustomAdapterCategory.ViewHolder>() {

    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.title_category);

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v)
        }
    }

    private var startClick = true;
    var row_index = -1; //индекс элесентов

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = data[position];
        holder.title.text = data[position].title;


        holder.title.setOnClickListener {

            if(TempData.finish)
            {
                row_index = position;
                TempData.selectCategory = position + 1;

                notifyDataSetChanged();

                TempData().sortProduct();
            }

        }

        if (row_index == position) {
            // Устанавливаем оранжевый цвет на нажатый элементы
            holder.title.setTextColor(holder.itemView.context.getColor(R.color.main_orange));
        }
        else
        {
            // Устанавливаем серый цвет на все остальные элементы
            holder.title.setTextColor(holder.itemView.context.getColor(R.color.gray));
        }

        if (TempData.selectCategory-1  == position && startClick) {
            // Устанавливаем оранжевый цвет на первый элемент
            holder.title.setTextColor(holder.itemView.context.getColor(R.color.main_orange));
            startClick = false;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_category, parent, false)

        return ViewHolder(view, View.OnClickListener {} )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

