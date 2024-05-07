package com.example.coffeeshop_20.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterCategory
import com.example.coffeeshop_20.Adapters.CustomAdapterProduct
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import java.time.LocalTime

class FragmentMenu : Fragment() {


    lateinit var mRecyclerCategory: RecyclerView;
    lateinit var  mRecyclerProduct: RecyclerView;

    companion object{
        lateinit var customAdapterCategory: CustomAdapterCategory;
        var customAdapterProduct  = CustomAdapterProduct(TempData.sortProductArray)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        init(view)
        selectData();

        val currentHour = LocalTime.now().hour


        val timeText = view.findViewById<TextView>(R.id.timeText)
        timeText.text = checkTimeOfDay(currentHour)




        return view;
    }

    private fun init(view: View){
        mRecyclerCategory = view.findViewById(R.id.category_list)
        mRecyclerProduct = view.findViewById(R.id.product_list);
    }
    private fun selectData()
    {
        customAdapterCategory = CustomAdapterCategory(TempData.categoryArray)
        mRecyclerCategory.adapter = customAdapterCategory;
        mRecyclerProduct.adapter = customAdapterProduct;
        mRecyclerCategory.scrollToPosition(TempData.selectCategory - 1 )
    }

    fun checkTimeOfDay(hour: Int): String {
        return when (hour) {
            in 6..10->"Доброе утро"
            in 11..17 -> "Хорошего вам дня"
            in 18..23 -> "Хорошего вам вечера"
            in 0..5 -> "Доброй ночи"
            else -> "Ой"
        }
    }
}