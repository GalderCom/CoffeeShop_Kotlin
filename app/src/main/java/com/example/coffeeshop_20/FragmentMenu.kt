package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FragmentMenu : Fragment() {



    private var selectCategory = 1;
    private var selectButton = 1;
    private var selectCoffeeBakery = true;

    private lateinit var buttonCoffee:Button;
    private lateinit var buttonBakery:Button;

    private lateinit var typeClassic:LinearLayout;
    private lateinit var typeAutor:LinearLayout;
    private lateinit var typeRaf:LinearLayout;
    private lateinit var typeCold:LinearLayout;

    private lateinit var typeTextClassic:TextView;
    private lateinit var typeTextAutor:TextView;
    private lateinit var typeTextRaf:TextView;
    private lateinit var typeTextCold:TextView;
    //private lateinit var mRecyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var customAdapterMenu: CustomAdapterMenu;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__menu, container, false)

        init(view);
        unselectCategoryCoffee()
        selectCategoryCoffee(typeTextClassic);
        click(view);


        val supa = ConnectSupaBase();


        if(AllDataFromBase.coffeeArray.isEmpty())
        {
            lifecycleScope.launch {
                customAdapterMenu = CustomAdapterMenu(supa.getDataCoffee())
                AllDataFromBase.coffeeArray = supa.getDataCoffee();
                sortingCoffee(view)
            }
        }
        else
        {
            sortingCoffee(view)
        }

        return view;
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun sortingCoffee(view: View)
    {
        val arrayCoffee = ArrayList<DataClass.Coffee>()

        val mRecyclerView:RecyclerView = view.findViewById(R.id.listMenu1)

        val customAdapter = CustomAdapterMenu(arrayCoffee)
        mRecyclerView.adapter = customAdapter;

        for (i in 0 until AllDataFromBase.coffeeArray.size) {
            if (AllDataFromBase.coffeeArray[i].categoryId == selectCategory) {
                arrayCoffee.add(AllDataFromBase.coffeeArray[i])
            }
            customAdapter.notifyDataSetChanged()
            mRecyclerView.isNotEmpty()
        }


    }
    private fun init(view: View){
        buttonCoffee = view.findViewById(R.id.button_coffee);
        buttonBakery = view.findViewById(R.id.button_bakery);

        typeClassic = view.findViewById(R.id.classic_type_coffee);
        typeAutor = view.findViewById(R.id.autor_type_coffee);
        typeRaf = view.findViewById(R.id.raf_type_coffee);
        typeCold = view.findViewById(R.id.cold_type_coffee);

        typeTextClassic = view.findViewById(R.id.classic_type_text_coffee);
        typeTextAutor = view.findViewById(R.id.autor_type_text_coffee);
        typeTextRaf = view.findViewById(R.id.raf_type_text_coffee);
        typeTextCold = view.findViewById(R.id.cold_type_text_coffee);

    }
    private fun click(view: View)
    {
        //coffee
        buttonCoffee.setOnClickListener()
        {
            if(selectButton != 1)
            {
                selectButton = 1
                unselectCoffeeBakery();
                selectCoffeeBakery(buttonCoffee);
                selectCoffeeBakery = true;
            }
        }

        //bakery
        buttonBakery.setOnClickListener(){
            if(selectButton != 2) {
                selectButton = 2
                unselectCoffeeBakery();
                selectCoffeeBakery(buttonBakery);
                selectCoffeeBakery = false;
            }
        }


        typeClassic.setOnClickListener(){
            if(selectCategory != 1) {
                unselectCategoryCoffee()
                selectCategoryCoffee(typeTextClassic);
                selectCategory = 1
                sortingCoffee(view)
            }
        }

        typeAutor.setOnClickListener(){
            if(selectCategory != 2) {
                unselectCategoryCoffee()
                selectCategoryCoffee(typeTextAutor);
                selectCategory = 2
                sortingCoffee(view)
            }
        }

        typeRaf.setOnClickListener(){
            if(selectCategory != 3) {
                unselectCategoryCoffee()
                selectCategoryCoffee(typeTextRaf);
                selectCategory = 3
                sortingCoffee(view)
            }
        }

        typeCold.setOnClickListener(){
            if(selectCategory != 4) {
                unselectCategoryCoffee()
                selectCategoryCoffee(typeTextCold);
                selectCategory = 4
                sortingCoffee(view)
            }
        }
    }

    private fun selectCoffeeBakery(btn:Button){
        btn.setBackgroundResource(R.drawable.menu_select_button)
    }
    private fun unselectCoffeeBakery(){
        buttonCoffee.setBackgroundResource(R.drawable.menu_un_select_button)
        buttonBakery.setBackgroundResource(R.drawable.menu_un_select_button)

    }
    @SuppressLint("ResourceAsColor")
    private fun selectCategoryCoffee(text:TextView){
        text.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BE4E28"))
    }


    private fun unselectCategoryCoffee(){
        val color = "#FFFFFF"

        typeTextAutor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))

        typeTextClassic.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))

        typeTextCold.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))

        typeTextRaf.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
    }
}