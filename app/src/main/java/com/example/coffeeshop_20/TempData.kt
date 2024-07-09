package com.example.coffeeshop_20

import android.annotation.SuppressLint
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.Fragments.FragmentMyOrder

class TempData {

    companion object{

        var productArray:ArrayList<DataClass.Products> = ArrayList();
        var sortProductArray: ArrayList<DataClass.Products> = ArrayList();
        var categoryArray:ArrayList<DataClass.Category> = ArrayList();
        var favorArray:ArrayList<DataClass.Favor> = ArrayList();
        val genderArray:ArrayList<DataClass.Gender> = arrayListOf(DataClass.Gender(1,"Мужской"), DataClass.Gender(2,"Женский"))
        var saveAddressArray:ArrayList<DataClass.SaveAddress> = ArrayList();
        var ordersArray:ArrayList<DataClass.Orders> = ArrayList();
        var orderActive: ArrayList<DataClass.Orders> = ArrayList();

        var addressArray: ArrayList<String> = ArrayList()
        var statusArray:ArrayList<DataClass.Status> = ArrayList()
        var cartArray:ArrayList<DataClass.Cart> = ArrayList()

        var newOrder : DataClass.Orders = DataClass.Orders()
        var newCart: ArrayList<DataClass.Cart> = ArrayList();


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
                catch (_: Exception) { }
            }
        }
    }

    fun sortOrders(orders: ArrayList<DataClass.Orders>){
        // Сортировка по id по возрастанию
        orders.sortByDescending { it.id }

        // Создание временного списка для элементов с status = 2
        val status2Orders = orders.filter { it.status == "Оформлен" || it.status == "Доставляется" || it.status == "Принят" }

        // Удаление элементов с status = 2 из исходного списка
        orders.removeAll(status2Orders)

        // Создание нового отсортированного списка, добавление элементов с status = 2 в начало
         ordersArray =  orders
         orderActive = status2Orders as ArrayList<DataClass.Orders>

        try {
            FragmentMyOrder.customAdapterOrder.notifyDataSetChanged()
            FragmentMyOrder.customAdapterOrderActive.notifyDataSetChanged()
        } catch (ex: Exception) {
            // Handle exception
        }

    }

}