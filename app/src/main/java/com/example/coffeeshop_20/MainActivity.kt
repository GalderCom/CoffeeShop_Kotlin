package com.example.coffeeshop_20

import android.net.http.HttpResponseCache.install
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false);

       /* getData();*/
        WorkWithMenu();
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
        }


        val buttonOrder: LinearLayout = findViewById(R.id.menu_orderButton)
        buttonOrder.setOnClickListener {
            UnSelect(textMenuORDER)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_center1);
        }


        val buttonHeart: LinearLayout = findViewById(R.id.menu_heartButton)
        buttonHeart.setOnClickListener {
            UnSelect(textMenuHEART)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_center2);
        }


        val buttonAccount: LinearLayout = findViewById(R.id.menu_accountButton)
        buttonAccount.setOnClickListener {
            UnSelect(textMenuACCOUNT)
            glassLayout.setBackgroundResource(R.drawable.menu_glass_end1);
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