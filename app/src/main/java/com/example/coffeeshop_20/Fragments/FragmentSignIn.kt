package com.example.coffeeshop_20.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.Activitys.ActivitySignUp
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import kotlinx.coroutines.launch


class FragmentSignIn : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)




    }
}