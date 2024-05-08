package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.Fragments.FragmentFavourites
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.runBlocking

class CustomAdapterFavorites(private var data: ArrayList<DataClass.Favor>): RecyclerView.Adapter<CustomAdapterFavorites.ViewHolder>(){
    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {


        val title: TextView = itemView.findViewById(R.id.fvrTitle);
        val price: TextView = itemView.findViewById(R.id.fvrPrice);
        val image: ImageView = itemView.findViewById(R.id.fvrImage);
        val btnRemove: Button = itemView.findViewById(R.id.removeFvr);
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
            if(data[position].id_product == TempData.productArray[i].id) {
                holder.price.text = TempData.productArray[i].price.toString();
                holder.title.text = TempData.productArray[i].title
                holder.image.setImageDrawable(TempData.productArray[i].image)


                holder.itemView.setOnClickListener() {


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
                    weight.text = TempData.productArray[i].weight;

                    val btnFvr = view.findViewById<ImageView>(R.id.favorbtn)
                    btnFvr.visibility = View.GONE

                    val image = view.findViewById<ImageView>(R.id.imageVew);
                    image.setImageDrawable(TempData.productArray[i].image)

                    val desc = view.findViewById<TextView>(R.id.desrView);
                    desc.text = TempData.productArray[i].description;

                    val title = view.findViewById<TextView>(R.id.titleView);
                    title.text = TempData.productArray[i].title

                    // Отображение BottomSheet
                    bottomSheetDialog.show()
                }
            }
        }

        var lastClickTime: Long = 0
        holder.btnRemove.setOnClickListener(){

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 5000) { // Проверяем, прошло ли уже 1 секунда с последнего клика
                ConnectSupaBase().removeFavor(data[position].id_product)
                lastClickTime = currentTime // Обновляем время последнего клика
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