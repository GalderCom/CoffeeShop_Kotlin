package com.example.coffeeshop_20.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.Fragments.FragmentProfile
import com.example.coffeeshop_20.Fragments.FragmentSaveAddress
import com.example.coffeeshop_20.Fragments.FragmentSignIn
import com.example.coffeeshop_20.Fragments.FragmentSignUp
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlin.system.exitProcess

class ActivityStart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportFragmentManager.beginTransaction().replace(
            R.id.mainFragmentContainer,
            FragmentSignIn(),"SignIn"
        ).commit();
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fragment =  supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)

        when(fragment?.tag)
        {
            "signUp"-> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.mainFragmentContainer,
                    FragmentSignIn(), "SignIn"
                ).commit()
            }
            "SignIn" ->{
                super.onBackPressed()
            }
        }
    }
}