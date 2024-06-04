package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.runBlocking


class FragmentSignIn : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val btnStart: Button = view.findViewById(R.id.btn_start);
        val emailView = view.findViewById<EditText>(R.id.editText_Email);

        emailView.setText(TempData.user.email)


        btnStart.setOnClickListener(){

            if(isEmailValid(emailView.text.toString()))
            {
                try {
                    TempData.user.email = emailView.text.toString()
                    runBlocking {
                        //Производим авторизацию пользователя
                        ConnectSupaBase().signIn();
                    }
                    parentFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        FragmentConfirmCode(),"OTP"
                    ).commit();
                }
                catch (ex:Exception) {

                    val valueInBrackets = ex.message.toString();
                    //Если пользователь не найден производится переходн на фрагмент регистрации
                    if(valueInBrackets.contains("Signups not allowed for otp"))
                    {

                        parentFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer,
                            FragmentSignUp(),"signUp"
                        ).commit();
                        btnStart.visibility = View.GONE;

                    }
                    else if(valueInBrackets.contains("60 second")) {
                        //если код запрошен и пользователь перезашел в приложение
                        parentFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer,
                            FragmentConfirmCode(),"OTP"
                        ).commit();
                    }

                    else{
                        Toast.makeText(view.context, ex.toString(), Toast.LENGTH_SHORT).show()
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