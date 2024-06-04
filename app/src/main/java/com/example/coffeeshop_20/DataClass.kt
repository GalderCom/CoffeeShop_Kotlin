package com.example.coffeeshop_20

import android.graphics.drawable.Drawable

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
        var birthday:String = "01.01.2000",
        var gender:Int = 1,
        var email: String = ""
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
        var id: Int = 0,
        var street:String = "",
        var name:String = "",
        var house:String = "",
        var entrance:String = "",
        var floor:String = "",
        var flat: String = "",
        var comm: String = ""
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
    @kotlinx.serialization.Serializable
    data class Orders(
        val id: Int = 0,
        val date: String = "" ,
        val address: String = "",
        val price: Int = 0,
        val status: String = "",
        val time: String = ""
    )

    @kotlinx.serialization.Serializable
    data class OrderInsert(
        val id: Int? = 0,
        val id_user: String = "",
        val date: String = "" ,
        val id_address: Int = 0,
        val price: Int = 0,
        val status: Int = 0,
        val time: String = ""
    )
    @kotlinx.serialization.Serializable
    data class Cart(
        val id: Int = 0,
        val id_product: Int = 0,
        var count_: Int = 0,
        var id_order:Int = 0
    )
    data class Status(
        val id: Int = 0,
        val name: String = ""
    )

}