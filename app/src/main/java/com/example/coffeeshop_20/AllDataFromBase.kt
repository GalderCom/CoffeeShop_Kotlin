package com.example.coffeeshop_20

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AllDataFromBase {
    companion object{
        var coffeeArray:ArrayList<DataClass.Coffee> = ArrayList();

        suspend fun setCoffeeArray()
        {
            val supa = ConnectSupaBase();
            val temp = supa.getDataCoffee()
            coffeeArray = temp;
        }
        suspend fun getCoffeeArray(): ArrayList<DataClass.Coffee>
        {
            return coffeeArray;
        }
    }


}