package com.example.coffeeshop_20

import android.media.Image

class DataClass {
    data class Coffee(val id: Int,
                      val name: String,
                      val description: String,
                      val shortDescription: String,
                      val image: Int,
                      val categoryId: Int,
                      val price: Int)

}