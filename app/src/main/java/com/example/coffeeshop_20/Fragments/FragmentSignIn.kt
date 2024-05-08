package com.example.coffeeshop_20.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.Activitys.ActivityStart
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FragmentSignIn : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val btnStart: Button = view.findViewById(R.id.btn_start);

        btnStart.setOnClickListener(){
            val emailView = view.findViewById<EditText>(R.id.editText_Email);
            TempData.email = emailView?.text.toString();

            if(isEmailValid(emailView.text.toString()))
            {
                try {
                    runBlocking {
                        ConnectSupaBase().signIn();
                    }
                   /* val  intent = Intent(context, ActivityMain::class.java)
                    startActivity(intent)*/

                    parentFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        FragmentConfirmCode()
                    ).commit();

                 //   startActivity(Intent(context, ActivityMain::class.java))
                }
                catch (ex:Exception) {

                    val text = ex.message.toString();

                    val valueInBrackets = text.substringAfter("(").substringBefore(")")

                    if(valueInBrackets == "Invalid login credentials"){

                        parentFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer,
                            FragmentSignUp(),"signUp"
                        ).commit();
                        btnStart.visibility = View.GONE;
                    }
                    else if(valueInBrackets.length > 100)
                    {
                        if(valueInBrackets.contains("60 second")) {
                            Toast.makeText(view.context, "Дождитесь повторной отправки кода", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(view.context, "Нет сети", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                emailView.error = "Не верный формат"
            }
        }
        return view;
    }
    fun isEmailValid(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
        return regex.matches(email)
    }
}