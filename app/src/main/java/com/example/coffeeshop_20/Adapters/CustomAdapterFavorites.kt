package com.example.coffeeshop_20.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.Fragments.FragmentCart
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomAdapterFavorites(private var data: ArrayList<DataClass.Favor>): RecyclerView.Adapter<CustomAdapterFavorites.ViewHolder>(){
    class ViewHolder(itemView: View, private val listener: View.OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val title: TextView = itemView.findViewById(R.id.fvrTitle);
        val price: TextView = itemView.findViewById(R.id.fvrPrice);
        val image: ImageView = itemView.findViewById(R.id.fvrImage);
        val btnRemove: Button = itemView.findViewById(R.id.removeFvr);
        //val btnAdd: ImageView = itemView.findViewById(R.id.btnAdd)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(v)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = data[position];

       /* holder.btnAdd.setOnClickListener() {

            if (TempData.newCart.size == 0){
                val tempCartItem = DataClass.Cart(id_product =  data[position].id_product, count =  1, id_order =  TempData.newOrder.id)
                TempData.newCart.add(tempCartItem)
                holder.btnAdd.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.favor_mark))
                notifyDataSetChanged()
            }
            else
            {

                if(holder.btnAdd.drawable == holder.itemView.context.getDrawable(R.drawable.favor_plus)) {
                    for (i in 0 until TempData.newCart.size){
                        if(TempData.newCart[i].id_product == data[position].id_product) {
                            TempData.newCart.removeAt(i)
                            break
                        }
                    }
                    holder.btnAdd.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.favor_plus))
                } else {
                    holder.btnAdd.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.favor_mark))
                    val tempCartItem = DataClass.Cart(id_product = data[position].id_product, count = 1, id_order = TempData.newOrder.id)
                    TempData.newCart.add(tempCartItem)
                }
                notifyDataSetChanged()


            }



        }
*/
        for (i in  0 until  TempData.productArray.size ){ //step 1
            if(data[position].id_product == TempData.productArray[i].id) {
                holder.price.text = TempData.productArray[i].price.toString();
                holder.title.text = TempData.productArray[i].title
                holder.image.setImageDrawable(TempData.productArray[i].image)

              /*  if (TempData.newCart.size != 0) {
                    for (j in 0 until TempData.newCart.size) {
                        if (TempData.newCart[j].id_product == data[position].id_product) {
                            holder.btnAdd.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.favor_mark))
                            break;
                        }
                    }
                }*/

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

                    val btnAdd2 = view.findViewById<Button>(R.id.addView)
                    btnAdd2.setOnClickListener(){

                        if (btnAdd2.text == "Добавить"){
                            val tempCartItem = DataClass.Cart(id_product =  data[position].id_product, count_ = 1 , id_order = TempData.newOrder.id)
                            TempData.newCart.add(tempCartItem)
                            btnAdd2.backgroundTintList = view.context.getResources().getColorStateList(R.color.nice_gray);

                            btnAdd2.setTextColor(holder.itemView.context.getColor(R.color.white))
                            btnAdd2.setText("Убрать")
                        }
                        else
                        {
                            for (i in 0 until TempData.newCart.size){
                                if(TempData.newCart[i].id_product == data[position].id_product)
                                {
                                    TempData.newCart.remove(TempData.newCart[i])
                                    break
                                }
                            }
                            btnAdd2.backgroundTintList = null
                            btnAdd2.setTextColor(holder.itemView.context.getColor(R.color.white))
                            btnAdd2.setText("Добавить")
                        }

                        try {
                            FragmentCart.customAdapterCart.notifyDataSetChanged()
                        }
                        catch (_:Exception){}
                    }

                    if (TempData.newCart.size != 0) {
                        for (j in 0 until TempData.newCart.size) {
                            if (TempData.newCart[j].id_product == data[position].id_product) {
                                btnAdd2.setTextColor(holder.itemView.context.getColor(R.color.white))
                                btnAdd2.backgroundTintList = view.context.getResources().getColorStateList(R.color.nice_gray);
                                btnAdd2.setText("Убрать")
                                break;
                            }
                        }
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

        holder.btnRemove.setOnClickListener(){
            GlobalScope.launch {
                ConnectSupaBase().removeFavor(data[position].id_product)
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