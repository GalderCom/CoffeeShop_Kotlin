package com.example.coffeeshop_20.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.example.coffeeshop_20.databinding.FragmentConfirmCodeBinding
import kotlinx.coroutines.runBlocking
import java.util.Timer
import java.util.TimerTask


class FragmentConfirmCode : Fragment() {
   lateinit var sendCod: TextView
   lateinit var timerView :TextView
   lateinit var timerLayout :LinearLayout

    private var _binding: FragmentConfirmCodeBinding? = null
    private val binding get() = _binding!!
   
   
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentConfirmCodeBinding.inflate(inflater,container,false)
        
        val view = binding.root
        sendCod  = binding.btnSendCode
        timerView = binding.timerLabel
        timerLayout = binding.layoutTimer


        val emailText = binding.emailText

        emailText.setText(TempData.user.email);

        val editText1: EditText = binding.editText1
        val editText2: EditText = binding.editText2
        val editText3: EditText = binding.editText3
        val editText4: EditText = binding.editText4
        val editText5: EditText = binding.editText5
        val editText6: EditText = binding.editText6
        val editText7: EditText = binding.editText7

        val editTexts = listOf(editText1, editText2, editText3, editText4, editText5, editText6,editText7)





        startTimer(view)

        sendCod.setOnClickListener(){
            timerLayout.visibility = View.VISIBLE
            sendCod.visibility = View.GONE
            startTimer(view)

            ConnectSupaBase().signIn()
        }

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

        val value = arguments?.getString("value", "null")

        if(value == "signUp")
        {
            val label = binding.label
            label.text = "Регистрация";
        }


        val btn = binding.buttonContinueCode
        btn.setOnClickListener() {

            var code = "";
            var empty = false;

           // Цикл для прохода по всем элементам массива editTexts, кроме последнего
            for (i in 0 until editTexts.size - 1) {
                // Проверяем, не пустое ли значение в текущем элементе editText
                if (editTexts[i].text.isNotEmpty()) {
                    // Если не пустое, добавляем значение в переменную code
                    code += editTexts[i].text.toString()
                } else {
                    // Если пустое значение, устанавливаем флаг empty в true
                    empty = true
                }
            }

            runBlocking {
                // Проверяем, все ли поля были заполнены
                if (!empty) {
                    // Проверяем верность кода с помощью функции verifyConfCode из класса ConnectSupaBase
                    when (ConnectSupaBase().verifyConfCode(code, value.toString(), view)) {
                        // Если код верен
                        true -> {
                            // Создаем Intent для перехода на ActivityMain

                            if (TempData.user.name != "")
                            {
                                val intent = Intent(context, ActivityMain::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }
                            else
                            {
                                Toast.makeText(view.context, "Введите необходимые данные", Toast.LENGTH_SHORT).show()
                                parentFragmentManager.beginTransaction().replace(
                                    R.id.mainFragmentContainer,
                                    FragmentSignUp(), "SignUp"
                                ).commit()
                            }
                        }
                        // Если код неверен
                        false -> {
                            Toast.makeText(view.context, "Не верный код", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Показываем сообщение о том, что не все поля заполнены
                    Toast.makeText(view.context, "Введите код", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    fun startTimer(view: View){
        TempData.timer = 60;
        val timerView = binding.timerLabel
        val timerLayout = binding.layoutTimer

        val handler = Handler(Looper.getMainLooper())
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                // Update the UI on the main thread
                handler.post {
                    TempData.timer--
                    if (TempData.timer == 0) {
                        timer.cancel()
                        timerLayout.visibility = View.GONE
                        sendCod.visibility = View.VISIBLE
                    } else {
                        timerView.text = TempData.timer.toString() + "с"
                    }
                }
            }
        }, 0, 1000)
    }


}