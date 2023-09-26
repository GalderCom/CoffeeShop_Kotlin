package com.example.coffeeshop_20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class Fragment_Profile : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        var view: View? = null
        view = inflater.inflate(R.layout.fragment__profile, container, false)

        val MyDataClick: TextView = view.findViewById(R.id.textView_my_data);
        MyDataClick.setOnClickListener(){
           parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_MyData(),"MY_DATA").commit();
        }

        val MyOrderClick: TextView = view.findViewById(R.id.textView_my_order);
        MyOrderClick.setOnClickListener(){
            parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_MyData(),"MY_DATA").commit();
        }
        val MySaveAdresClick: TextView = view.findViewById(R.id.textView_my_saveAdres);
        MySaveAdresClick.setOnClickListener(){
            parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_MyData(),"MY_DATA").commit();
        }

        // Inflate the layout for this fragment
        return view
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_Profile().apply {

            }

    }

}