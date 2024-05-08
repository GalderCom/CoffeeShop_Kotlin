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
        val price: Int = 0,
        val imageUri: String)
    data class Category(val id: Int = 1,
                        val title: String = "")
    @kotlinx.serialization.Serializable
    data class User(
        val id:String = "",
        var name:String ="",
        var birthday:String = "01-01-2000",
        var gender:Int = 1
    )

    data class Favor(
        val id: Int = 1,
        val id_user: String = "",
        var id_product:Int = 1
    )
    @kotlinx.serialization.Serializable
    data class FavorInsert(
        val id_user: String = "",
        val id_product:Int = 0
    )

    data class Gender(
        val id: Int = 0,
        val name:String = ""
    )
    @kotlinx.serialization.Serializable
    data class SaveAddress(
        val id: Int = 0,
        val street:String = "",
        val name:String = "",
        val house:String = "",
        val entrance:String = "",
        val floor:String = "",
        val flat: String = "",
        val comm: String = ""
    )
    @kotlinx.serialization.Serializable
    data class SaveAddressInsert(
        val id_user: String ="",
        val street:String = "",
        val name:String = "",
        val house:String = "",
        val entrance:String = "",
        val floor:String = "",
        val flat: String = "",
        val comm: String = ""
    )



}