package com.example.coffeeshop_20.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterCategory
import com.example.coffeeshop_20.Adapters.CustomAdapterProduct
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.example.coffeeshop_20.databinding.FragmentMenuBinding
import java.time.LocalTime

class FragmentMenu : Fragment() {


    lateinit var mRecyclerCategory: RecyclerView;
    lateinit var  mRecyclerProduct: RecyclerView;

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

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

        _binding = FragmentMenuBinding.inflate(inflater,container,false)
        val view = binding.root
        val currentHour = LocalTime.now().hour


        val timeText = binding.timeText
        mRecyclerCategory = binding.categoryList
        mRecyclerProduct = binding.productList


        timeText.text = checkTimeOfDay(currentHour)
        selectData();

        return view;
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