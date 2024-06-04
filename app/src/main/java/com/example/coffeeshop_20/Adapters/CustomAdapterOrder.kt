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

class CustomAdapterOrder (private var data: ArrayList<DataClass.Orders>): RecyclerView.Adapter<CustomAdapterOrder.ViewHolder>() {

    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val address: TextView = itemView.findViewById(R.id.address_order);
        val date: TextView = itemView.findViewById(R.id.date_order);
        val status: TextView = itemView.findViewById(R.id.status);
        val price: TextView = itemView.findViewById(R.id.price)


        val recycler: RecyclerView = itemView.findViewById(R.id.cart_list)


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

        holder.status.text = data[position].status

        // Проверяем статус заказа и присваиваем необхлдимый цвет текста
        when(data[position].status)
        {
            "Отменен"->{holder.status.setTextColor(holder.itemView.context.getColor(R.color.red))}
            "Выполнен"->{holder.status.setTextColor(holder.itemView.context.getColor(R.color.green))}
            "Оформлен"->{holder.status.setTextColor(holder.itemView.context.getColor(R.color.white))}
            "Доставляется"->{holder.status.setTextColor(holder.itemView.context.getColor(R.color.white))}
        }

// Устанавливаем дату, адрес и цену заказа
        holder.date.text = data[position].date
        holder.address.text = data[position].address
        holder.price.text = data[position].price.toString()

// Создаем временный список для товаров данного заказа
        var tempCart: ArrayList<DataClass.Cart> = ArrayList()
// Проходим по всем товарам в корзине
        for(i in 0 until TempData.cartArray.size){
            // Если товар принадлежит данному заказу (по id заказа)
            if(TempData.cartArray[i].id_order == data[position].id)
            {
                // Добавляем товар во временный список
                tempCart.add(TempData.cartArray[i])
                // Создаем адаптер и устанавливаем его для RecyclerView
                val adapter = CustomAdapterCartInOrder(tempCart)
                holder.recycler.adapter = adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_order, parent, false)

        return ViewHolder(view, View.OnClickListener {} )
    }

    override fun getItemCount(): Int {
        return data.size
    }
}