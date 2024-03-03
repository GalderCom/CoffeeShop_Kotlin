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


    lateinit var mRecyclerCategory: RecyclerView;
    lateinit var  mRecyclerProduct: RecyclerView;
    companion object{
        lateinit var customAdapterProduct: CustomAdapterProduct;
        lateinit var customAdapterCategory: CustomAdapterCategory;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__menu, container, false)

        init(view)
        selectData();

        return view;
    }

    private fun init(view: View){
        mRecyclerCategory = view.findViewById(R.id.category_list)
        mRecyclerProduct = view.findViewById(R.id.product_list);
    }
    private fun selectData()
    {
        var tempArray = TempData.productArray;
        for (i in 0 until tempArray.size)
        {
            if(tempArray[i].id_category == TempData.selectCategory)
            {
                tempArray.remove(tempArray[i]);
            }
        }


        customAdapterCategory = CustomAdapterCategory(TempData.categoryArray)
        customAdapterProduct = CustomAdapterProduct(tempArray);



        //update
        mRecyclerCategory.adapter = customAdapterCategory;
        mRecyclerProduct.adapter = customAdapterProduct;
    }
}