package com.example.coffeeshop_20

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.icu.text.CaseMap.Title
import android.media.Image
import android.os.VibrationEffect.Composition
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toDatePeriod
import java.util.Date

class DataClass {

    data class Products(
        val id: Int = 0,
        val title: String = "",
        val description: String = "",
        val weight: String = "",
        var image: Drawable?,
        val id_category: Int = 0,
        val price: Int = 0)
    @kotlinx.serialization.Serializable
    data class Category(val id: Int = 1,
                        val title: String = "")
    @kotlinx.serialization.Serializable
    data class User(
        val id:String = "",
        val name:String ="",
        val birthday:String = "01-01-2000"
    )

}