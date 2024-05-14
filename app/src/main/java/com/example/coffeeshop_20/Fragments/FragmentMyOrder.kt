package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterOrder
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.example.coffeeshop_20.newDialogView

class FragmentMyOrder : Fragment() {

    lateinit var  mRecyclerOrder: RecyclerView;
    lateinit var customAdapterOrder: CustomAdapterOrder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_order, container, false)


        mRecyclerOrder = view.findViewById(R.id.order_list)
        customAdapterOrder = CustomAdapterOrder(TempData.ordersArray)
        mRecyclerOrder.adapter = customAdapterOrder


        val buttonBack: ImageButton = view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(){

            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, FragmentProfile()).commit()

        }

        return view
    }
}