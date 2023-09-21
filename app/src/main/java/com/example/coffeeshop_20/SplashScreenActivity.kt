package com.example.coffeeshop_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowCompat
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.SignInActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        WindowCompat.setDecorFitsSystemWindows(window, false);

        Handler(Looper.getMainLooper()).postDelayed({
            val intent  =Intent(this,SignInActivity::class.java)
            startActivity(intent);
            finish();
        },1000)
    }
}