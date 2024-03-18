package com.example.coffeeshop_20.Activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import kotlinx.coroutines.launch

class ActivitySignIn : AppCompatActivity() {

    lateinit var emailView: EditText;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init();


        WindowCompat.setDecorFitsSystemWindows(window, false);
        funNumberPhone();



    }

    fun init()
    {
        emailView = findViewById(R.id.editText_Email);
    }

    private fun funNumberPhone()
    {
//        val numberPhone : EditText = findViewById(R.id.editText_numberPhone);

        emailView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                   /* if(s.length < 2) {
                        numberPhone.setText("+7")
                    }*/
                 /*   numberPhone.setSelection(s.length)*/
                }
            }
        })
    }



    fun Click_SignIn(view: View) {



        val email = emailView.text.toString();

        if(emailView.text.length > 5)
        {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            lifecycleScope.launch {



                try {
                    ConnectSupaBase().signIn(email);

                    val intent1 = Intent(view.context, ActivityMain::class.java)
                    startActivity(intent1)
                    finish()
                }
                catch (ex:Exception) {



                    val intent1 = Intent(view.context, ActivitySignUp::class.java)

                    val bundle = Bundle()

                    bundle.putString("Email", email)

                    intent1.putExtras(bundle)

                    startActivity(intent1)
                    finish()

                }



            }
        }

    }

}