package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterAddressSelect
import com.example.coffeeshop_20.Adapters.CustomAdapterCart
import com.example.coffeeshop_20.Adapters.CustomAdapterFavorites
import com.example.coffeeshop_20.Adapters.CustomAdapterTime
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class FragmentCart : Fragment() {

    lateinit var  mRecyclerCart: RecyclerView;

    companion object{
        lateinit var customAdapterCart: CustomAdapterCart;
        lateinit var customAdapterTime: CustomAdapterTime;

        lateinit var priceView:TextView
        lateinit var label: TextView

        var timeArray: ArrayList<String> = ArrayList()



        lateinit var textAddress: TextView
        lateinit var mRecyclerViewAddress: RecyclerView
        lateinit var imageArrow: ImageView

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart,container,false)



        label = view.findViewById(R.id.label)
        priceView = view.findViewById(R.id.priceViewCart)

        mRecyclerCart = view.findViewById(R.id.cart_list)
        customAdapterCart = CustomAdapterCart(TempData.newCart)
        mRecyclerCart.adapter = customAdapterCart

        customAdapterTime = CustomAdapterTime(timeArray)

        mRecyclerCart.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewAttachedToWindow(view: View) {
               update()
            }

            override fun onChildViewDetachedFromWindow(view: View) {
               update()
            }
        })

        val btn = view.findViewById<Button>(R.id.btn_making_order)
        btn.setOnClickListener(){

            workTime()

            val layout = LayoutInflater.from(view.context);



            val view2= layout.inflate(R.layout.bottom_sheet_time, null)


            val bottomSheetDialog = BottomSheetDialog(view.context)
            val mRecyclerTime = view2.findViewById<RecyclerView>(R.id.time_list)
            mRecyclerTime.adapter = customAdapterTime
            bottomSheetDialog.setContentView(view2)
            imageArrow =  view2.findViewById<ImageView>(R.id.arrowGedner);
            mRecyclerViewAddress = view2.findViewById(R.id.address_list)
            textAddress = view2.findViewById(R.id.genderText)

            textAddress.setOnClickListener(){
                viewRecyclerGender(view)
            }
            imageArrow.setOnClickListener(){
                viewRecyclerGender(view)
            }

            val adapter = CustomAdapterAddressSelect(TempData.saveAddressArray)
            mRecyclerViewAddress.adapter = adapter



            // Отображение BottomSheet
            bottomSheetDialog.show()

        }

        return view
    }
    fun viewRecyclerGender(view: View)
    {
        if(mRecyclerViewAddress.visibility == View.GONE)
        {
            mRecyclerViewAddress.visibility = View.VISIBLE
            imageArrow.rotation = view.rotation.minus(90);
        }
        else
        {
            mRecyclerViewAddress.visibility = View.GONE
            imageArrow.rotation = view.rotation.plus(90);
        }
    }
    fun workTime(){
        val sdf = SimpleDateFormat("HH:mm")
        val currentTime = Date()
        val currentTimeString = sdf.format(currentTime)
        val currentTimeInMinutes = currentTimeString.split(":")[0].toInt() * 60 + currentTimeString.split(":")[1].toInt()

        val arrayList = ArrayList<String>()

        val startTime = 8 * 60
        val endTime = 20 * 60

        for (time in startTime until endTime step 30) {
            if (arrayList.size < 8 && time >= currentTimeInMinutes) {
                val hour = time / 60
                val minute = time % 60
                val timeString = "%02d:%02d".format(hour, minute)
                if(timeArray.size != 8){
                    timeArray.add(timeString)
                    customAdapterTime.notifyDataSetChanged()

                }
                else{
                    break
                }

            }
        }

    }

    fun update()
    {

        if (customAdapterCart.itemCount != 0)
        {
            label.visibility = View.GONE;

            var tempPrice: Int = 0
            if (TempData.newCart.size != 0){
                for(i in 0 until  TempData.newCart.size)
                {
                    for (j in 0 until TempData.productArray.size)
                    {
                        if(TempData.productArray[j].id == TempData.newCart[i].id_product){
                            tempPrice += TempData.newCart[i].count * TempData.productArray[j].price
                            break
                        }
                    }
                }
                priceView.text = tempPrice.toString()
            }
        }
        else
        {
            label.visibility = View.VISIBLE;
            priceView.text = "0"

        }


    }
}