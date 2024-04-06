package com.example.coffeeshop_20.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.launch

class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        WindowCompat.setDecorFitsSystemWindows(window, false);

        TempData.context = this



        lifecycleScope.launch()
        {
            ConnectSupaBase().selectCategory();
        }
        Handler(Looper.getMainLooper()).postDelayed({
           // val intent  = Intent(this, ActivitySignIn::class.java)
            val intent  = Intent(this, ActivityStart::class.java)
            startActivity(intent);
            finish();
        },1000)


    }
}