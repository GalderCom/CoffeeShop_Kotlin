package com.example.coffeeshop_20.Activitys

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import kotlinx.coroutines.launch

class ActivitySignUp : AppCompatActivity() {

    var email = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)


        val bundle = intent.extras

        email = bundle!!.getString("Email", "null");

    }
    fun Click_SignUp(view: View) {
        val nameView = findViewById<EditText>(R.id.nameView);
        val genderView = findViewById<EditText>(R.id.genderView);
        val birthdayView = findViewById<EditText>(R.id.birthdayView);

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        lifecycleScope.launch {
            ConnectSupaBase().signUp(email);
            ConnectSupaBase().insertUser(email,nameView.text.toString(),birthdayView.text.toString(),1);
        }
    }
}