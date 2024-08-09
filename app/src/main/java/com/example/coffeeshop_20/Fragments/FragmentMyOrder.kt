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
import com.example.coffeeshop_20.databinding.FragmentMyOrderBinding
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
    private var _binding: FragmentMyOrderBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyOrderBinding.inflate(inflater,container,false)
        val view = binding.root

        mRecyclerOrder = binding.orderList
        mRecyclerOrderActive = binding.activeOrderList

        customAdapterOrder = CustomAdapterOrder(TempData.ordersArray)
        customAdapterOrderActive = CustomAdapterOrder(TempData.orderActive)

        mRecyclerOrder.adapter = customAdapterOrder
        mRecyclerOrderActive.adapter = customAdapterOrderActive


        val buttonBack = binding.buttonBack
        buttonBack.setOnClickListener(){

            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, FragmentProfile()).commit()

        }


        val label = binding.label
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

        val line = binding.line
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


        val updateBtn = binding.buttonUpdate
        updateBtn.setOnClickListener(){
            GlobalScope.launch {
                ConnectSupaBase().selectOrder()
                ConnectSupaBase().selectCart()
            }


        }

        return view
    }
}