package com.example.coffeeshop_20.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterGender
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.SbObject
import com.example.coffeeshop_20.TempData
import io.github.jan.supabase.postgrest.postgrest

class FragmentMyData : Fragment() {


    @SuppressLint("StaticFieldLeak")
    companion object{
        lateinit var textViewGender:TextView;
        lateinit var arrowGender: ImageView;
        lateinit var mRecyclerGender: RecyclerView;
        lateinit var birthDayText: EditText;
        lateinit var crossBtn: ImageView;
        lateinit var nameText: TextView;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
       val  view = inflater.inflate(R.layout.fragment__my_data, container, false)

        val buttonBack: ImageButton = view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(){
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentProfile()
            ).commit();
        }



        init(view)
        crossBtn.setOnClickListener(){
            birthDayText.text.clear();
        }


        TempData.selectedGender = TempData.user.gender;

        when(TempData.user.gender)
        {
            1 -> {textViewGender.text = "Мужской"};
            2 -> {textViewGender.text = "Женский"};
        }

        val btnSave = view.findViewById<Button>(R.id.btn_save);
        btnSave.setOnClickListener(){
            var errorDate = false

            if (birthDayText.text.length == 10|| nameText.text.length >= 2 ) {

                try {

                    val lastFourChars = birthDayText.text.takeLast(4)
                    if (lastFourChars.substring(0, 4).toInt() > 2024 || lastFourChars.substring(0, 4).toInt() < 1800) {
                        errorDate = true;
                    }

                    val startTwoChars = birthDayText.text.take(2)
                    if (startTwoChars.substring(0, 2).toInt() > 31) {
                        errorDate = true;
                    }

                    val tempChars = birthDayText.text.take(5)
                    val centerChars = tempChars.takeLast(2)
                    if (centerChars.substring(0, 2).toInt() > 12) {
                        errorDate = true;
                    }

                    if(errorDate)
                    {
                        Toast.makeText(view.context,"Ошибка в дате",Toast.LENGTH_LONG).show()
                    }

                    else
                    {
                        if(TempData.user.name != nameText.text.toString() || TempData.selectedGender != TempData.user.gender || TempData.user.birthday != birthDayText.text.toString())
                        {
                            TempData.user.name = nameText.text.toString()
                            TempData.user.birthday = birthDayText.text.toString()


                            TempData.user.gender = TempData.selectedGender;

                            ConnectSupaBase().updateUser();
                            Toast.makeText(view.context,"Изменения сохранены",Toast.LENGTH_LONG).show()

                            parentFragmentManager.beginTransaction().replace(
                                R.id.mainFragmentContainer,
                                FragmentProfile()
                            ).commit();
                        }
                        else
                        {
                            Toast.makeText(view.context,"Вы не изменили данные!",Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (ex: Exception)
                {
                    Toast.makeText(view.context,ex.toString(),Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(view.context,"Заполните обязательные поля",Toast.LENGTH_LONG).show()
            }


        }

        birthDayText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

                if(s?.length != 10)
                {
                    birthDayText.error;
                }
                try {
                    if (s?.length == 2 || s?.length == 5) {
                        s.append('.')
                    }
                }
                catch (ex: Exception)
                {
                    birthDayText.text.clear();
                }

            }
        })

       birthDayText.setText(TempData.user.birthday)

        val btnGender = view.findViewById<FrameLayout>(R.id.btnGender);

        textViewGender.setOnClickListener(){
            viewRecyclerGender(view)
        }
        btnGender.setOnClickListener(){
            viewRecyclerGender(view)
        }

        return view;
    }
    fun init(view : View)
    {
        arrowGender = view.findViewById<ImageView>(R.id.arrowGedner);
        val customAdapterGender = CustomAdapterGender(TempData.genderArray);
        textViewGender = view.findViewById<TextView>(R.id.genderText);
        mRecyclerGender = view.findViewById<RecyclerView>(R.id.gender_list);
        mRecyclerGender.adapter = customAdapterGender;
        mRecyclerGender.visibility = View.GONE
        birthDayText = view.findViewById(R.id.birthdayView);
        crossBtn = view.findViewById(R.id.crossBirthday)
        nameText = view.findViewById(R.id.nameView);

        val nameUser = view.findViewById<EditText>(R.id.nameView);
        nameUser.setText(TempData.user.name)
    }
    fun viewRecyclerGender(view: View)
    {
        if(mRecyclerGender.visibility == View.GONE)
        {
            mRecyclerGender.visibility = View.VISIBLE
            arrowGender.rotation = view.rotation.minus(90);
        }
        else
        {
            mRecyclerGender.visibility = View.GONE
            arrowGender.rotation = view.rotation.plus(90);
        }
    }


}