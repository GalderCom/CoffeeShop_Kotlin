package com.example.coffeeshop_20

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ActivityMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,FragmentMenu()).commit();


       /* getData();*/




        WorkWithMenu();
    }

    var backPressedTime: Long = 0


    override fun onBackPressed() {
        val my_data =  supportFragmentManager.findFragmentByTag("MY_DATA")
        if(my_data != null && my_data.isVisible())
        {
            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,FragmentProfile()).commit();
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

        val glassLayout: LinearLayout = findViewById(R.id.bottomNavigationLayout);


        val buttonMenu: LinearLayout = findViewById(R.id.menu_menuButton)
        buttonMenu.setOnClickListener {
            UnSelect(textMenuMENU)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_start);
            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,FragmentMenu()).commit();
        }


        val buttonOrder: LinearLayout = findViewById(R.id.menu_orderButton)
        buttonOrder.setOnClickListener {
            UnSelect(textMenuORDER)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_center1);
            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,FragmentCart()).commit();
        }


        val buttonHeart: LinearLayout = findViewById(R.id.menu_heartButton)
        buttonHeart.setOnClickListener {
            UnSelect(textMenuHEART)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_center2);
            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,FragmentFavourites()).commit();
        }


        val buttonAccount: LinearLayout = findViewById(R.id.menu_accountButton)
        buttonAccount.setOnClickListener {
            UnSelect(textMenuACCOUNT)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_end1);

            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,FragmentProfile()).commit();


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