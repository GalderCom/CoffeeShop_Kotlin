package com.example.coffeeshop_20

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Explode
import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat

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