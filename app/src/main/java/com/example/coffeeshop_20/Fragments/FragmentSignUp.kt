package com.example.coffeeshop_20.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.R

class FragmentSignUp : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment__sign_up, container, false)


        //val spinner: Spinner = view.findViewById(R.id.spinner);

        val btn  = view.findViewById<Button>(R.id.btn_signUp)

        btn.setOnClickListener()
        {
            val intent1 = Intent(context, ActivityMain::class.java)
            startActivity(intent1)
        }


        return view
    }

}