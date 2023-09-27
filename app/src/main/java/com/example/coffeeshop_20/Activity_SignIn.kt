package com.example.coffeeshop_20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.view.WindowCompat

class Activity_SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        WindowCompat.setDecorFitsSystemWindows(window, false);
        funNumberPhone();


    }

    private fun funNumberPhone()
    {
        val numberPhone : EditText = findViewById(R.id.editText_numberPhone);

        numberPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if(s.length < 2) {
                        numberPhone.setText("+7")
                    }
                    numberPhone.setSelection(s.length)
                }
            }
        })
    }



    fun Click_SignIn(view: View) {
        val numberPhone : EditText = findViewById(R.id.editText_numberPhone);

        if(numberPhone.text.length == 12)
        {
            val intent = Intent(this,Activity_CodeFromSMS::class.java)
            startActivity(intent);
            finish();
        }

    }

}