package com.example.coffeeshop_20.Adapters

import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Activitys.ActivityStart
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.Fragments.FragmentFavourites
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.SbObject
import com.example.coffeeshop_20.TempData
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray

class CustomAdapterProduct(private var data: ArrayList<DataClass.Products>): RecyclerView.Adapter<CustomAdapterProduct.ViewHolder>() {
    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name: TextView = itemView.findViewById(R.id.name);
        var weight: TextView = itemView.findViewById(R.id.short_description);
        var price: TextView = itemView.findViewById(R.id.price)
        var image: ImageView = itemView.findViewById(R.id.imageMenu)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = data[position];
        holder.name.text = data[position].title;
        holder.price.text = data[position].price.toString();
        holder.weight.text = data[position].weight;
        holder.image.setImageDrawable(data[position].image)

        holder.itemView.setOnClickListener(){


            val layout = LayoutInflater.from(holder.itemView.context);

            val bottomSheetDialog = BottomSheetDialog(holder.itemView.context)


            val view = layout.inflate(R.layout.bottom_sheet_fragment, null)
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()

           val btnClose = view.findViewById<ImageView>(R.id.crossView)
            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss();
            }

            val weight = view.findViewById<TextView>(R.id.weightView)
            weight.text = data[position].weight;

            val btnFvr = view.findViewById<ImageView>(R.id.favorbtn)

            for (j in 0 until  TempData.favorArray.size)
            {
                for (i in 0 until TempData.productArray.size)
                {
                    if (TempData.favorArray[j].id_product == data[position].id)
                    {
                        btnFvr.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.heart_orange));
                    }
                }
            }

            btnFvr.setOnClickListener {
                var searchItem = false;
                if (TempData.favorArray.size != 0) {
                    for(i in 0 until TempData.favorArray.size) {
                        if (data[position].id == TempData.favorArray[i].id_product) {
                            searchItem = true;
                            btnFvr.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.heart));
                            ConnectSupaBase().removeFavor(data[position].id);
                            break;
                        }
                    }

                    if (!searchItem) {
                        btnFvr.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.heart_orange));
                        ConnectSupaBase().insertFavor(data[position].id);
                    }

                } else {
                    btnFvr.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.heart_orange));
                    ConnectSupaBase().insertFavor(data[position].id);
                }
            }

            val image = view.findViewById<ImageView>(R.id.imageVew);
            image.setImageDrawable(data[position].image)

            val desc = view.findViewById<TextView>(R.id.desrView);
            desc.text = data[position].description;

            val title = view.findViewById<TextView>(R.id.titleView);
            title.text = data[position].title

            // Отображение BottomSheet
            bottomSheetDialog.show()

        }

        holder.itemView.findViewById<ImageView>(R.id.add_cartView).setOnClickListener(){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_menu, parent, false)
        return ViewHolder(view, View.OnClickListener {
        })

    }

    override fun getItemCount(): Int {
        return data.size
    }
}