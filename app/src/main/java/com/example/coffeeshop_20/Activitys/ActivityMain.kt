package com.example.coffeeshop_20.Activitys

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.coffeeshop_20.Fragments.FragmentProfile
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.launch

class ActivityMain : AppCompatActivity() {
    val ctx = ConnectSupaBase();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ConnectSupaBase().selectFavor();

        supportFragmentManager.beginTransaction().replace(
            R.id.mainFragmentContainer,
            FragmentMenu()
        ).commit();
        WorkWithMenu();

        lifecycleScope.launch {

           var toast = Toast.makeText(TempData.context, "Получаем меню...",Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();

            Handler(Looper.getMainLooper()).postDelayed({
                TempData.finish = false;
                ConnectSupaBase().selectImage();
                TempData.finish = true;
            },100)

        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val myData =  supportFragmentManager.findFragmentByTag("MY_DATA")
        if(myData != null && myData.isVisible)
        {
            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentProfile()
            ).commit();
        }
        else{
            finish()
        }
    }


    private lateinit var textMenuMENU: TextView;
    private lateinit var textMenuORDER: TextView;

    private lateinit var textMenuHEART: TextView;
    private lateinit var textMenuACCOUNT: TextView;
    private fun WorkWithMenu()
    {


        textMenuMENU = findViewById(R.id.menu_home)
        textMenuORDER = findViewById(R.id.menu_order)
        textMenuHEART = findViewById(R.id.menu_heart)
        textMenuACCOUNT  = findViewById(R.id.menu_account)

        //val glassLayout: LinearLayout = findViewById(R.id.bottomNavigationLayout);



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

        if(  view.visibility != View.VISIBLE)
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