package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import com.example.coffeeshop_20.Adapters.CustomAdapterCategory
import com.example.coffeeshop_20.Fragments.FragmentMenu

class TempData {

    companion object{

        var productArray:ArrayList<DataClass.Products> = ArrayList();
        var sortProductArray: ArrayList<DataClass.Products> = ArrayList();

        var categoryArray:ArrayList<DataClass.Category> = ArrayList();

        var selectCategory = 1;

        var imageProduct: ArrayList<Drawable> = ArrayList();

    }
    @SuppressLint("NotifyDataSetChanged")
    fun sortProduct()
    {
        sortProductArray.clear();
        for( i in productArray.size-1 downTo     0)
        {
            if(productArray[i].id_category == selectCategory)
            {
                sortProductArray.add(productArray[i])
                FragmentMenu.customAdapterProduct.notifyDataSetChanged();
            }
        }
    }

}