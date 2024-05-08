package com.example.coffeeshop_20.Activitys

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telecom.Call
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.Fragments.FragmentCart
import com.example.coffeeshop_20.Fragments.FragmentFavourites
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.Fragments.FragmentMyData
import com.example.coffeeshop_20.Fragments.FragmentProfile
import com.example.coffeeshop_20.Fragments.FragmentSaveAddress
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.launch
import java.time.LocalTime
import kotlin.system.exitProcess

class ActivityMain : AppCompatActivity() {

   lateinit var ctx :ActivityMain;
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var bottomNavigationLayout: LinearLayout;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ConnectSupaBase().selectFavor();
        ConnectSupaBase().selectUserAddress()

        ctx = this;

        bottomNavigationLayout = findViewById(R.id.bottomNavigationLayout);


        supportFragmentManager.beginTransaction().replace(
            R.id.mainFragmentContainer,
            FragmentMenu()
        ).commit();
        WorkWithMenuBtn();

        lifecycleScope.launch {

            Handler(Looper.getMainLooper()).postDelayed({
                TempData.finish = false;
                ConnectSupaBase().selectImage();
                TempData.finish = true;
            },100)

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        val fragment =  supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)

        when(fragment?.tag)
        {
            "My_Data" , "Save_Address", "My_Order"-> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.mainFragmentContainer,
                    FragmentProfile()
                ).commit()
            }
            "Add_Address"-> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.mainFragmentContainer, FragmentSaveAddress(),"Save_Address").commit()
            }
            else ->{
                if (fragment !is FragmentMenu) {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        FragmentMenu()
                    ).commit()
                    UnSelect(textMenuMENU)
                }
                else
                {
                    super.onBackPressed()
                    moveTaskToBack(true);
                    exitProcess(-1)
                }
            }
        }

    }

    private lateinit var textMenuMENU: TextView;
    private lateinit var textMenuORDER: TextView;

    private lateinit var textMenuHEART: TextView;
    private lateinit var textMenuACCOUNT: TextView;
    
    private fun WorkWithMenuBtn()
    {
        textMenuMENU = findViewById(R.id.menu_home)
        textMenuORDER = findViewById(R.id.menu_order)
        textMenuHEART = findViewById(R.id.menu_heart)
        textMenuACCOUNT  = findViewById(R.id.menu_account)

        
        val buttonMenu: LinearLayout = findViewById(R.id.menu_menuButton)
        buttonMenu.setOnClickListener {
            UnSelect(textMenuMENU)
            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentMenu()
            ).commit();
        }


        val buttonOrder: LinearLayout = findViewById(R.id.menu_orderButton)
        buttonOrder.setOnClickListener {
            UnSelect(textMenuORDER)

            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentCart()
            ).commit();
        }


        val buttonHeart: LinearLayout = findViewById(R.id.menu_heartButton)
        buttonHeart.setOnClickListener {
            UnSelect(textMenuHEART)
            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentFavourites()
            ).commit();
        }


        val buttonAccount: LinearLayout = findViewById(R.id.menu_accountButton)
        buttonAccount.setOnClickListener {
            UnSelect(textMenuACCOUNT)

            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentProfile()
            ).commit();
        }
    }


    private fun UnSelect(view: View) {

        if(view.visibility != View.VISIBLE)
        {
            val tempTextView1 : TextView = findViewById(R.id.menu_home)
            tempTextView1.visibility = View.GONE;

            val tempTextView2 : TextView = findViewById(R.id.menu_order)
            tempTextView2.visibility = View.GONE;

            val tempTextView3 : TextView = findViewById(R.id.menu_heart)
            tempTextView3.visibility = View.GONE;

            val tempTextView4 : TextView = findViewById(R.id.menu_account)
            tempTextView4.visibility = View.GONE;

            view.visibility = View.VISIBLE
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_splash_menu));
        }
    }



}