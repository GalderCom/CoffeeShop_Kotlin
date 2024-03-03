package com.example.coffeeshop_20.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowCompat
import com.example.coffeeshop_20.R

class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        WindowCompat.setDecorFitsSystemWindows(window, false);






        Handler(Looper.getMainLooper()).postDelayed({
           // val intent  = Intent(this, ActivitySignIn::class.java)
            val intent  = Intent(this, ActivityMain::class.java)
            startActivity(intent);
            finish();
        },1000)


    }
}