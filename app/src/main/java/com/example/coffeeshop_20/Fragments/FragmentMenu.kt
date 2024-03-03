package com.example.coffeeshop_20.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.Adapters.CustomAdapterCategory
import com.example.coffeeshop_20.Adapters.CustomAdapterProduct
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.launch

class FragmentMenu : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__menu, container, false)

        //click(view);

        //select category

        val mRecyclerCategory: RecyclerView = view.findViewById(R.id.category_list)
        val mRecyclerProduct : RecyclerView = view.findViewById(R.id.product_list);

        lifecycleScope.launch {

            if(TempData.category.isEmpty() && TempData.productArray.isEmpty())
            {
                TempData.category = ConnectSupaBase().selectCategory();
                TempData.productArray = ConnectSupaBase().selectProducts();
            }


            val customAdapterCategory = CustomAdapterCategory(TempData.category)
            val customAdapterProduct = CustomAdapterProduct(TempData.productArray);

            //update
            mRecyclerCategory.adapter = customAdapterCategory;
            customAdapterCategory.notifyDataSetChanged();

            mRecyclerProduct.adapter = customAdapterProduct;
            customAdapterProduct.notifyDataSetChanged();
        }




        return view;
    }
}