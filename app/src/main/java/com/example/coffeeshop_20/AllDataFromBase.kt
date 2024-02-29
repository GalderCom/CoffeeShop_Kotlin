package com.example.coffeeshop_20

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AllDataFromBase {

    companion object{

        var coffeeArray:ArrayList<DataClass.Coffee> = ArrayList();
        var bakeryArray:ArrayList<DataClass.Bakery> = ArrayList();

        var category:ArrayList<DataClass.Category> = ArrayList();

    }


}