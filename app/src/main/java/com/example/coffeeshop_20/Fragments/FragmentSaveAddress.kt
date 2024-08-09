package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterSaveAddress
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.example.coffeeshop_20.databinding.FragmentSaveAddressBinding

class FragmentSaveAddress : Fragment() {

    lateinit var  mRecyclerAdr: RecyclerView;

    companion object{
        lateinit var customAdapterAddress: CustomAdapterSaveAddress;
    }

    private var _binding: FragmentSaveAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveAddressBinding.inflate(inflater,container,false)
        val view = binding.root


        val label = binding.nullArray
        mRecyclerAdr = binding.saveAddressList
        customAdapterAddress = CustomAdapterSaveAddress(TempData.saveAddressArray)

        if(!TempData.saveAddressArray.isEmpty())
        {
            mRecyclerAdr.adapter = customAdapterAddress;
            label.visibility = View.GONE
        }
        else
        {
            label.visibility = View.VISIBLE
        }

        val addBtn = binding.btnAddAddress
        addBtn.setOnClickListener(){

            if(TempData.saveAddressArray.size != 4){
                parentFragmentManager.beginTransaction().replace(
                    R.id.mainFragmentContainer,
                    FragmentAddAddress(),"Add_Address").commit();
            }
            else
            {
                Toast.makeText(view.context,"Удалите один адрес",Toast.LENGTH_LONG).show()
            }

        }

        val btnBack = binding.btnBack
        btnBack.setOnClickListener(){
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentProfile()).commit();
        }

        return view;
    }
}