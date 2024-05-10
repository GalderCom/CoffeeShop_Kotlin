package com.example.coffeeshop_20.Activitys

import android.content.Context
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
import com.example.coffeeshop_20.newDialogView
import kotlin.system.exitProcess

class ActivityStart : AppCompatActivity() {

    lateinit var context:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        context = this;

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
                val dialog = newDialogView(context = context)
                dialog.text.setText("Выйти?")
                dialog.setPositiveButtonClickListener(){
                    dialog.dismiss()
                    super.onBackPressed()
                }
                dialog.setNegativeButtonClickListener(){
                    dialog.dismiss()
                }
                dialog.show()
            }
            "OTP" ->{
                val dialog = newDialogView(context = context)
                dialog.text.setText("Вернутся назад?")
                dialog.setPositiveButtonClickListener(){
                    dialog.dismiss()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        FragmentSignIn()).commit();
                }
                dialog.setNegativeButtonClickListener(){
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }
}