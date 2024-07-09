package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterOrder
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FragmentMyOrder : Fragment() {

    companion object{
        lateinit var  mRecyclerOrder: RecyclerView;
        lateinit var mRecyclerOrderActive: RecyclerView;
        lateinit var customAdapterOrder: CustomAdapterOrder
        lateinit var customAdapterOrderActive: CustomAdapterOrder
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_order, container, false)





        mRecyclerOrder = view.findViewById(R.id.order_list)
        mRecyclerOrderActive = view.findViewById(R.id.active_order_list)

        customAdapterOrder = CustomAdapterOrder(TempData.ordersArray)
        customAdapterOrderActive = CustomAdapterOrder(TempData.orderActive)

        mRecyclerOrder.adapter = customAdapterOrder
        mRecyclerOrderActive.adapter = customAdapterOrderActive



        val buttonBack: ImageButton = view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(){

            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, FragmentProfile()).commit()

        }


        val label: TextView = view.findViewById(R.id.label)
        mRecyclerOrder.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewAttachedToWindow(view: View) {

                if (customAdapterOrder.itemCount != 0)
                {
                    label.visibility = View.GONE;
                }
                else
                {
                    label.visibility = View.VISIBLE;
                }

            }

            override fun onChildViewDetachedFromWindow(view: View) {

                    if (customAdapterOrder.itemCount != 0)
                    {
                        label.visibility = View.GONE;
                    }
                    else
                    {
                        label.visibility = View.VISIBLE;
                    }

            }
        })

        val line = view.findViewById<ImageView>(R.id.line)
        mRecyclerOrderActive.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewAttachedToWindow(view: View) {

                if (customAdapterOrderActive.itemCount == 0)
                {
                    line.visibility = View.GONE;
                }
                else
                {
                    line.visibility = View.VISIBLE;
                }

            }

            override fun onChildViewDetachedFromWindow(view: View) {

                if (customAdapterOrderActive.itemCount == 0)
                {
                    line.visibility = View.GONE;
                }
                else
                {
                    line.visibility = View.VISIBLE;
                }

            }
        })


        val updateBtn = view.findViewById<ImageView>(R.id.button_update)
        updateBtn.setOnClickListener(){
            GlobalScope.launch {
                ConnectSupaBase().selectOrder()
                ConnectSupaBase().selectCart()
            }


        }

        return view
    }
}