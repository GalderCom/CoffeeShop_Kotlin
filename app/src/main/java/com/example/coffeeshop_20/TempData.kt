package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import com.example.coffeeshop_20.Adapters.CustomAdapterCategory
import com.example.coffeeshop_20.Adapters.CustomAdapterProduct
import com.example.coffeeshop_20.Fragments.FragmentMenu

class TempData {

    companion object{

        var productArray:ArrayList<DataClass.Products> = ArrayList();
        var sortProductArray: ArrayList<DataClass.Products> = ArrayList();
        var categoryArray:ArrayList<DataClass.Category> = ArrayList();
        var favorArray:ArrayList<DataClass.Favor> = ArrayList();
        val genderArray:ArrayList<DataClass.Gender> = arrayListOf(DataClass.Gender(1,"Мужской"),
                                                                  DataClass.Gender(2,"Женский"))
        var saveAddressArray:ArrayList<DataClass.SaveAddress> = ArrayList();

        var addressArray: ArrayList<String> = ArrayList()

        var selectCategory = 1;
        var selectGender = 1

        var user : DataClass.User = DataClass.User();
        var finish: Boolean = true;

        var timer = 60;
    }
    @SuppressLint("NotifyDataSetChanged")
    fun sortProduct()
    {
        sortProductArray.clear();
        for( i in 0 until      productArray.size)
        {
            if(productArray[i].id_category == selectCategory)
            {
                sortProductArray.add(productArray[i])

                try {
                    FragmentMenu.customAdapterProduct.notifyDataSetChanged();
                }
                catch (_: Exception)
                {
                }
            }
        }
    }

}