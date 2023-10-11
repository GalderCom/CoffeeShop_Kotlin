package com.example.coffeeshop_20

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityCodeFromSMS : AppCompatActivity() {

    private lateinit var number1: EditText;
    private lateinit var number2: EditText;
    private lateinit var number3: EditText;
    private lateinit var number4: EditText;
    private lateinit var button: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_from_sms)
        init();
        WorkingWithCode();

    }
    private fun init()
    {
        //editText
        number1 = findViewById(R.id.number_1);
        number2 = findViewById(R.id.number_2);
        number3 = findViewById(R.id.number_3);
        number4 = findViewById(R.id.number_4);

        //button
        button = findViewById(R.id.button_Continue_code);
    }

    private fun WorkingWithCode()
    {


        number1.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if(s.length == 1) {
                        number2.requestFocus()
                    }
                }
            }
        })

        number2.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if(s.length == 1) {
                        number3.requestFocus()
                    }

                }
            }
        })

        number2.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && number2.text.isEmpty()) {

                number1.requestFocus()
                return@OnKeyListener true
            }
            false
        })

        number3.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if(s.length == 1) {
                        number4.requestFocus()
                    }
                }
            }
        })

        number3.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && number3.text.isEmpty()) {

                number2.requestFocus()
                return@OnKeyListener true
            }
            false
        })

        number4.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!=null)
                {
                    if(s.length == 1) {

                    }

                }


            }
        })

        number4.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && number4.text.isEmpty()) {

                number3.requestFocus()
                return@OnKeyListener true
            }
            false
        })
    }


    fun Click_Continue_code(view: View) {



        if(getCodeEditText() == "1234")
        {

            Toast.makeText(applicationContext,"Код верный",Toast.LENGTH_SHORT).show();
            val intent = Intent(this,ActivityMain::class.java)
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(applicationContext,"Код не верный",Toast.LENGTH_SHORT).show();
        }


    }

    private fun getCodeEditText(): String{
        var Code:String = "";

        if(number1.text.isNotEmpty() && number2.text.isNotEmpty() && number3.text.isNotEmpty() && number4.text.isNotEmpty())
        {
           Code = "${number1.text}${number2.text}${number3.text}${number4.text}"
        }

        return Code;
    }
}