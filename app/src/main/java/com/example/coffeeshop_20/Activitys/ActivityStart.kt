package com.example.coffeeshop_20.Activitys

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.Fragments.FragmentMyData
import com.example.coffeeshop_20.Fragments.FragmentSignIn
import com.example.coffeeshop_20.R
import kotlinx.coroutines.launch

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

                if(selectedFragment == "SignIn")
                {
                    lifecycleScope.launch {

                        try {
                            ConnectSupaBase().signIn(email);

                            val intent1 = Intent(context, ActivityMain::class.java)
                            startActivity(intent1)
                            finish()

                        }
                        catch (ex:Exception) {
                            supportFragmentManager.beginTransaction().replace(
                                R.id.mainFragmentContainer,
                                FragmentMyData()
                            ).commit();
                            selectedFragment = "SignUp";
                            btnStart.text = "Регистрировать"
                        }

                    }
                }
                else if(selectedFragment == "SignUp")
                {
                    val nameView = findViewById<EditText>(R.id.nameView);
                    val genderView = findViewById<EditText>(R.id.genderView);
                    val birthdayView = findViewById<EditText>(R.id.birthdayView);
                    val btn  = findViewById<Button>(R.id.btn_myData)
                    btn.visibility = View.GONE;

                     lifecycleScope.launch {
                         ConnectSupaBase().signUp(email);
                         ConnectSupaBase().insertUser(email,nameView.text.toString(),birthdayView.text.toString());

                         val intent1 = Intent(context, ActivityMain::class.java)
                         startActivity(intent1)
                         finish()
                     }

                }

            }
        }
    }
}