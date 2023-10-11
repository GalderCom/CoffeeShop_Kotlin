package com.example.coffeeshop_20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton


class FragmentMyData : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
       val  view = inflater.inflate(R.layout.fragment__my_data, container, false)

        val buttonBack: ImageButton = view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(){
            parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,FragmentProfile()).commit();
        }

        // Inflate the layout for this fragment
        return view;


    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            FragmentMyData().apply {

            }
    }


}