package com.example.coffeeshop_20.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.google.android.material.bottomsheet.BottomSheetDialog

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


// Настройка прозрачного фона BottomSheet
           val btnClose = view.findViewById<ImageView>(R.id.crossView)
            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss();
            }

            val image = view.findViewById<ImageView>(R.id.imageVew);
            image.setImageDrawable(data[position].image)

            val desc = view.findViewById<TextView>(R.id.desrView);
            desc.text = data[position].description;

            val title = view.findViewById<TextView>(R.id.titleView);
            title.text = data[position].title

// Отображение BottomSheet
            bottomSheetDialog.show()


          /*
            // on below line we are creating a new bottom sheet dialog.
            val dialog = BottomSheetDialog(holder.itemView.context)

            // on below line we are inflating a layout file which we have created.
            val view = layout.inflate(R.layout.bottom_sheet_fragment, null)

            // on below line we are creating a variable for our button
            // which we are using to dismiss our dialog.

            // below line is use to set cancelable to avoid
            // closing of dialog box when clicking on the screen.
            dialog.setCancelable(false)

            // on below line we are setting
            // content view to our view.
            dialog.setContentView(view)

            // on below line we are calling
            // a show method to display a dialog.
            dialog.show()*/

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