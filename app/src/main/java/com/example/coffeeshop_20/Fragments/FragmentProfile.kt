package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.coffeeshop_20.R

class FragmentProfile : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        var view: View? = null
        view = inflater.inflate(R.layout.fragment__profile, container, false)

        val MyDataClick: FrameLayout = view.findViewById(R.id.frameLayout_my_data);
        MyDataClick.setOnClickListener(){
           parentFragmentManager.beginTransaction().replace(
               R.id.mainFragmentContainer,
               FragmentMyData(),"MY_DATA").commit();
        }


        // Inflate the layout for this fragment
        return view
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentProfile().apply {

            }

    }

}