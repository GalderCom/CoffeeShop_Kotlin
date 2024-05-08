package com.example.coffeeshop_20.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.runBlocking


class FragmentConfirmCode : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_confirm_code, container, false)

        val emailText = view.findViewById<TextView>(R.id.emailText)
        emailText.setText(TempData.email);




        val editText1: EditText = view.findViewById(R.id.editText1)
        val editText2: EditText = view.findViewById(R.id.editText2)
        val editText3: EditText = view.findViewById(R.id.editText3)
        val editText4: EditText = view.findViewById(R.id.editText4)
        val editText5: EditText = view.findViewById(R.id.editText5)
        val editText6: EditText = view.findViewById(R.id.editText6)
        val editText7: EditText = view.findViewById(R.id.editText7)

        val editTexts = listOf(editText1, editText2, editText3, editText4, editText5, editText6,editText7)

        for (i in 0 until editTexts.size -1) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        editTexts[i + 1].requestFocus()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            editTexts[i].setOnKeyListener { _, keyCode, event ->
                if (keyCode == android.view.KeyEvent.KEYCODE_DEL && event.action == android.view.KeyEvent.ACTION_DOWN) {
                    if (editTexts[i].text.isEmpty() && i > 0) {
                        editTexts[i - 1].requestFocus()
                        editTexts[i - 1].setText("");
                    }
                }
                false
            }
        }



        val btn = view.findViewById<Button>(R.id.button_Continue_code);
        btn.setOnClickListener(){

            var code = "";
            for (i in 0 until editTexts.size -1) {
               code +=  editTexts[i].text.toString()
            }

            runBlocking {
                when(  ConnectSupaBase().verifyConfCode(code)){
                    true -> {
                        val  intent = Intent(context, ActivityMain::class.java)
                        startActivity(intent)
                        ActivityMain().finish();
                    }
                    false -> {
                        Toast.makeText(view.context, "Не верный код", Toast.LENGTH_SHORT).show()
                    }
                }

            }


        }




        return view
    }



}