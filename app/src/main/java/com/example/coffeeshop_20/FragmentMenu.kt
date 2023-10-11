package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlinx.coroutines.launch
import java.text.ParsePosition

class FragmentMenu : Fragment() {



    private var selectType = "CLASSIC";
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var mRecyclerView:RecyclerView;
    private lateinit var mHostActivity:Activity;
    private lateinit var arrayT :ArrayList<DataClass.Coffee>;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__menu, container, false)






        init(view);

        unselectTypeCoffee()
        selectTypeCoffee(typeTextClassic);

        Click();




        val supa = ConnectSupaBase();
       // arrayT = ArrayList()
        mRecyclerView = view.findViewById(R.id.listMenu1)





      //  val customAdapter = CustomAdapterMenu(mHostActivity,arrayT)


        lifecycleScope.launch {
            mRecyclerView.adapter = CustomAdapterMenu(supa.getDataCoffee())
        }



        return view;
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mHostActivity = activity;
    }
    override fun onResume() {


        super.onResume()


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
    private fun Click()
    {
        //coffee
        buttonCoffee.setOnClickListener()
        {
            unselectCoffeeBakery();
            selectCoffeeBakery(buttonCoffee);
            selectCoffeeBakery = true;
        }

        //bakery
        buttonBakery.setOnClickListener(){
            unselectCoffeeBakery();
            selectCoffeeBakery(buttonBakery);
            selectCoffeeBakery = false;
        }



        typeClassic.setOnClickListener(){
            unselectTypeCoffee()
            selectTypeCoffee(typeTextClassic);
            selectType = "CLASSIC"
        }

        typeAutor.setOnClickListener(){
            unselectTypeCoffee()
            selectTypeCoffee(typeTextAutor);
            selectType = "AUTOR"
        }

        typeRaf.setOnClickListener(){
            unselectTypeCoffee()
            selectTypeCoffee(typeTextRaf);
            selectType = "RAF"
        }

        typeCold.setOnClickListener(){
            unselectTypeCoffee()
            selectTypeCoffee(typeTextCold);
            selectType = "COLD"
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
    private fun selectTypeCoffee(text:TextView){
        text.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BE4E28"))
    }

    @SuppressLint("ResourceAsColor")
    private fun unselectTypeCoffee(){
        val color = "#FFFFFF"

        typeTextAutor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))

        typeTextClassic.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))

        typeTextCold.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))

        typeTextRaf.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMenu().apply {
                arguments = Bundle().apply {
                }
            }
    }
}