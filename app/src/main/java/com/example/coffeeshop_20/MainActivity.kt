package com.example.coffeeshop_20

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.View.inflate
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false);

       window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_Menu()).commit();


       /* getData();*/




        WorkWithMenu();
    }

    var backPressedTime: Long = 0


    override fun onBackPressed() {

        if(textMenuACCOUNT.visibility == View.VISIBLE)
        {
            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_Profile()).commit();
        }
        else{
            onBackPressed()
        }
        backPressedTime = System.currentTimeMillis()
    }

    /*private fun getClient() : SupabaseClient{

        return createSupabaseClient(
            supabaseUrl = "https://fnnlozofxzdkvjkzyjzk.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZubmxvem9meHpka3Zqa3p5anprIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTUwNDU3MDAsImV4cCI6MjAxMDYyMTcwMH0.L799DC4l4sGGWoy2B_LR-Yv9jzJzqyjnykvx9FNF_XE"
        )
        {
            install(Postgrest)
        }

    }*/


   /* private fun getData()
    {
        lifecycleScope.launch {
            val client = getClient()
            val supabaseResponse= client.postgrest["Users"].select()
            val data = supabaseResponse.decodeList<User>()
            Log.e("supabase",data.toString());
        }
    }*/

    data class User(val id: Int, val name: String,val data_birthday: String,val phone: String, val photo_id: Int)

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
            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_Menu()).commit();
        }


        val buttonOrder: LinearLayout = findViewById(R.id.menu_orderButton)
        buttonOrder.setOnClickListener {
            UnSelect(textMenuORDER)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_center1);
            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_Cart()).commit();
        }


        val buttonHeart: LinearLayout = findViewById(R.id.menu_heartButton)
        buttonHeart.setOnClickListener {
            UnSelect(textMenuHEART)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_center2);
            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_Favourites()).commit();
        }


        val buttonAccount: LinearLayout = findViewById(R.id.menu_accountButton)
        buttonAccount.setOnClickListener {
            UnSelect(textMenuACCOUNT)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_end1);

            supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,Fragment_Profile()).commit();


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