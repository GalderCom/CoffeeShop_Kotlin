package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterCart
import com.example.coffeeshop_20.Adapters.CustomAdapterFavorites
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData

class FragmentCart : Fragment() {

    lateinit var  mRecyclerCart: RecyclerView;

    companion object{
        lateinit var customAdapterCart: CustomAdapterCart;
        var valueCount = 0;

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart,container,false)


       mRecyclerCart = view.findViewById(R.id.cart_list)
       customAdapterCart = CustomAdapterCart(TempData.newCart)
       mRecyclerCart.adapter = customAdapterCart


        return view
    }
}