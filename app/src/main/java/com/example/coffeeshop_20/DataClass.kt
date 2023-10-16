package com.example.coffeeshop_20

import android.media.Image
import android.os.VibrationEffect.Composition

class DataClass {
    data class Coffee(val id: Int,
                      val name: String,
                      val description: String,
                      val shortDescription: String,
                      val image: Int,
                      val categoryId: Int,
                      val price: Int)
    data class Bakery(val id: Int,
                      val name: String,
                      val description: String,
                      val image: Int,
                      val composition: String,
                      val price: Int,
                      val weight: String)

}