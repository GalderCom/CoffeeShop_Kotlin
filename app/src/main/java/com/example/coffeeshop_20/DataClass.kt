package com.example.coffeeshop_20

import android.icu.text.CaseMap.Title
import android.media.Image
import android.os.VibrationEffect.Composition

class DataClass {
    @kotlinx.serialization.Serializable
    data class Products(val id: Int = 0,
                      val title: String = "",
                      val description: String = "",
                      val weight: String = "",
                      val image: String = "0",
                      val id_category: Int = 0,
                      val price: Int = 0)
    @kotlinx.serialization.Serializable
    data class Category(val id: Int = 1,
                        val title: String = "")

}