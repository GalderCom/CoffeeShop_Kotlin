package com.example.coffeeshop_20.Fragments

import android.annotation.SuppressLint
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
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.Adapters.CustomAdapterAddressSelect
import com.example.coffeeshop_20.Adapters.CustomAdapterCart
import com.example.coffeeshop_20.Adapters.CustomAdapterTime
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class FragmentCart : Fragment() {

    lateinit var  mRecyclerCart: RecyclerView;
    @SuppressLint("StaticFieldLeak")
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
    @SuppressLint("SetTextI18n")
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


        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

                val btn = view.findViewById<Button>(R.id.btn_making_order)
                btn.setOnClickListener() {
                    if (currentHour <= 20) {
                        if (TempData.newCart.size != 0) {
                            if (TempData.saveAddressArray.size != 0) {
                                workTime()

                                val layout = LayoutInflater.from(view.context);

                                val view2 = layout.inflate(R.layout.bottom_sheet_making_order, null)

                                val price = view2.findViewById<TextView>(R.id.priceView)
                                price.text = priceView.text

                                val bottomSheetDialog = BottomSheetDialog(view.context)
                                val mRecyclerTime = view2.findViewById<RecyclerView>(R.id.time_list)
                                mRecyclerTime.adapter = customAdapterTime
                                bottomSheetDialog.setContentView(view2)
                                imageArrow = view2.findViewById<ImageView>(R.id.arrowGedner);
                                mRecyclerViewAddress = view2.findViewById(R.id.address_list)
                                textAddress = view2.findViewById(R.id.genderText)

                                textAddress.setOnClickListener() {
                                    viewRecyclerGender(view)
                                }
                                imageArrow.setOnClickListener() {
                                    viewRecyclerGender(view)
                                }

                                if (TempData.saveAddressArray.size != 0) {
                                    textAddress.text =
                                        TempData.saveAddressArray[0].name + ", " + TempData.saveAddressArray[0].street + ", " + TempData.saveAddressArray[0].house
                                }

                                val adapter = CustomAdapterAddressSelect(TempData.saveAddressArray)
                                mRecyclerViewAddress.adapter = adapter
                                adapter.id_address = TempData.saveAddressArray[0].id

                                val btnMakeOrder = view2.findViewById<Button>(R.id.btnMakeOrder)
                                btnMakeOrder.setOnClickListener() {
                                    runBlocking {

                                        val date = LocalDate.now()
                                        ConnectSupaBase().insertOrder(
                                            customAdapterTime.time,
                                            date.toString(),
                                            adapter.id_address,
                                            priceView.text.toString().toInt()
                                        )

                                        TempData.newCart.clear()
                                        customAdapterCart.notifyDataSetChanged()
                                        Toast.makeText(
                                            view2.context,
                                            "Заказз оформлен",
                                            Toast.LENGTH_SHORT
                                        ).show()


                                        parentFragmentManager.beginTransaction().replace(
                                            R.id.mainFragmentContainer,
                                            FragmentMyOrder(), "My_Order"
                                        ).commit();

                                        ActivityMain.bottomNavigationLayout.visibility = View.GONE


                                        bottomSheetDialog.dismiss()
                                    }
                                }

                                // Отображение BottomSheet
                                bottomSheetDialog.show()
                            }
                            else
                            {
                                Toast.makeText(view.context,"Добавте адрес для доставки!",Toast.LENGTH_SHORT).show()
                                parentFragmentManager.beginTransaction().replace(
                                    R.id.mainFragmentContainer,
                                    FragmentSaveAddress(), "Save_Address"
                                ).commit();

                                ActivityMain.bottomNavigationLayout.visibility = View.GONE
                            }
                        }
                        else
                        {
                            Toast.makeText(view.context,"Корзина пуста!",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        Toast.makeText(view.context,"Время!",Toast.LENGTH_SHORT).show()
                    }
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
        timeArray.clear()
        val sdf = SimpleDateFormat("HH:mm")
        val currentTime = Date()
        val currentTimeString = sdf.format(currentTime)
        val currentTimeInMinutes = currentTimeString.split(":")[0].toInt() * 60 + currentTimeString.split(":")[1].toInt()

        val arrayList = ArrayList<String>()

        val interval = 40

        val endTime = 20 * 60

        var newTimeInMinutes = currentTimeInMinutes + interval

        while (newTimeInMinutes < endTime && arrayList.size < 8) {
            val hour = newTimeInMinutes / 60
            val minute = newTimeInMinutes % 60
            val timeString = "%02d:%02d".format(hour, minute)

            arrayList.add(timeString)

            newTimeInMinutes += interval
        }

        timeArray.addAll(arrayList)
        customAdapterTime.notifyDataSetChanged()

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
                            tempPrice += TempData.newCart[i].count_ * TempData.productArray[j].price
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