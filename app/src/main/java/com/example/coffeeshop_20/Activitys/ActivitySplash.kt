package com.example.coffeeshop_20.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.Adapters.CustomAdapterProduct
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        WindowCompat.setDecorFitsSystemWindows(window, false);

        TempData.context = this

       val ctx = this;

        Handler().postDelayed({
            lifecycleScope.launch {
                while (true) {
                    try {
                        ConnectSupaBase().selectCategory();
                        ConnectSupaBase().selectProducts();
                        ConnectSupaBase().selectAddress();
                        TempData().sortProduct();

                        val intent = Intent(TempData.context, ActivityStart::class.java)
                        startActivity(intent);
                        finish();
                        break;
                    } catch (ex: Exception){
                        Toast.makeText(ctx, "Нет сети", Toast.LENGTH_SHORT).show()
                    }
                    delay(3000)
                }
            }
        }, 500)

    }
}