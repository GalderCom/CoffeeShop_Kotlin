package com.example.coffeeshop_20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowCompat

class Activity_Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        WindowCompat.setDecorFitsSystemWindows(window, false);

        Handler(Looper.getMainLooper()).postDelayed({
            val intent  = Intent(this, Activity_SignIn::class.java)
            startActivity(intent);
            finish();
        },1000)
    }
}