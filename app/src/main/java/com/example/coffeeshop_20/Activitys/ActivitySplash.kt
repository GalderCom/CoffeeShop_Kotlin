package com.example.coffeeshop_20.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.Adapters.CustomAdapterProduct
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import io.github.jan.supabase.gotrue.user.UserSession
import io.ktor.http.Url
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        WindowCompat.setDecorFitsSystemWindows(window, false);

        val loading = findViewById<ImageView>(R.id.loading)
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_anim)
        loading.startAnimation(rotateAnimation)

        val ctx = this;

        Handler().postDelayed({
            lifecycleScope.launch {
                while (true) {
                    try {
                        ConnectSupaBase().selectCategory();
                        ConnectSupaBase().selectProducts(ctx);
                        ConnectSupaBase().selectAddress();
                        ConnectSupaBase().selectStatus()
                        TempData().sortProduct();


                        if(ConnectSupaBase().loadSessionClient() == null){
                            val intent = Intent(ctx, ActivityStart::class.java)
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            ConnectSupaBase().selectUser()
                            TempData.selectGender = TempData.user.gender
                            ConnectSupaBase().selectFavor()
                            ConnectSupaBase().selectUserAddress()

                            val intent = Intent(ctx, ActivityMain::class.java)
                            startActivity(intent);
                            finish();
                        }


                        break;
                    } catch (ex: Exception){
                        val c = ex;
                        Toast.makeText(ctx, "Нет сети", Toast.LENGTH_SHORT).show()
                    }
                    delay(3000)
                }
            }
        }, 500)

    }
}