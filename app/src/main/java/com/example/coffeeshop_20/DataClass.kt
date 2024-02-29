package com.example.coffeeshop_20

import android.icu.text.CaseMap.Title
import android.media.Image
import android.os.VibrationEffect.Composition

class DataClass {
    @kotlinx.serialization.Serializable
    data class Coffee(val id: Int = 0,
                      val name: String = "",
                      val description: String = "",
                      val short_description: String = "",
                      val image: Int = 0,
                      val category_id: Int = 0,
                      val price: Int = 0)
    @kotlinx.serialization.Serializable
    data class Bakery(val id: Int = 0,
                      val name: String = "",
                      val description: String = "",
                      val image: Int = 0,
                      val composition: String = "",
                      val price: Int = 0,
                      val weight: String = "")
    @kotlinx.serialization.Serializable
    data class Category(val id: Int = 1,
                        val title: String = "")

}