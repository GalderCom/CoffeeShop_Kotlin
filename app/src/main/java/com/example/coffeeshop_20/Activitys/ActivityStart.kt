package com.example.coffeeshop_20.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.Fragments.FragmentSignIn
import com.example.coffeeshop_20.Fragments.FragmentSignUp
import com.example.coffeeshop_20.R

class ActivityStart : AppCompatActivity() {

    var selectedFragment = "SignIn"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)


        supportFragmentManager.beginTransaction().replace(
            R.id.mainFragmentContainer,
            FragmentSignIn()
        ).commit();

        val context = this;


        val btnStart:Button = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(){
            val emailView = findViewById<EditText>(R.id.editText_Email);
            val email = emailView?.text.toString();

            if(emailView?.text?.length!! > 5)
            {
                try {
                    ConnectSupaBase().signIn(email);
                    val intent1 = Intent(context, ActivityMain::class.java)
                    startActivity(intent1)
                    finish()

                }
                catch (ex:Exception) {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        FragmentSignUp()
                    ).commit();
                    btnStart.visibility = View.GONE;

                }

            }

        }

    }


}