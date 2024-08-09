package com.example.coffeeshop_20.Activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.Fragments.FragmentAddAddress
import com.example.coffeeshop_20.Fragments.FragmentCart
import com.example.coffeeshop_20.Fragments.FragmentFavourites
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.Fragments.FragmentMyData
import com.example.coffeeshop_20.Fragments.FragmentProfile
import com.example.coffeeshop_20.Fragments.FragmentSaveAddress
import com.example.coffeeshop_20.Fragments.FragmentSignUp
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.example.coffeeshop_20.databinding.ActivityMainBinding
import com.example.coffeeshop_20.newDialogView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActivityMain : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var ctx :ActivityMain;
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var bottomNavigationLayout: LinearLayout;

         /*lateinit var textMenuMENU: TextView ;
         lateinit var textMenuORDER: TextView;

         lateinit var textMenuHEART: TextView;
         lateinit var textMenuACCOUNT: TextView;*/
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (TempData.user.name == ""){
            Toast.makeText(this, "Введите необходимые данные", Toast.LENGTH_SHORT).show()
            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentSignUp(), "SignUp"
            ).commit()
            finish()
        }

        ConnectSupaBase().selectFavor();
        GlobalScope.launch {
            try {
                if(TempData.cartArray.isEmpty()){
                    ConnectSupaBase().selectUserAddress()
                    TempData.selectGender = TempData.user.gender
                    ConnectSupaBase().selectOrder()
                    ConnectSupaBase().selectCart()
                }
            }
            catch (ex: Exception){}

        }
        ctx = this;

        bottomNavigationLayout = binding.bottomNavigationLayout

        supportFragmentManager.beginTransaction().replace(
            R.id.mainFragmentContainer,
            FragmentMenu()
        ).commit();
        WorkWithMenuBtn();

        ConnectSupaBase().selectImage();

    }

    @SuppressLint("VisibleForTests")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        val fragment =  supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)

        when(fragment?.tag)
        {
            "Save_Address", "My_Order", "My_Order"-> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.mainFragmentContainer,
                    FragmentProfile()
                ).commit()
            }
            "My_Data"->{
                if(TempData.user.name != FragmentMyData.nameText.text.toString() || TempData.user.gender != TempData.selectGender || TempData.user.birthday != FragmentMyData.birthDayText.text.toString())
                {
                    val dialog = newDialogView(ctx)
                    dialog.text.setText("Вернутся без сохранения?")
                    dialog.setPositiveButtonClickListener(){
                        dialog.dismiss()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer, FragmentProfile()).commit()


                    }
                    dialog.setNegativeButtonClickListener(){
                        dialog.dismiss()
                    }
                    dialog.show()
                }
                else
                {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer, FragmentProfile()).commit()
                }

            }
            "Add_Address" -> {
                if(FragmentAddAddress.name.text.isNotEmpty() || FragmentAddAddress.comm.text.isNotEmpty() || FragmentAddAddress.house.text.isNotEmpty() || FragmentAddAddress.floor.text.isNotEmpty() || FragmentAddAddress.entrance.text.isNotEmpty() || FragmentAddAddress.flat.text.isNotEmpty() || FragmentAddAddress.streetText.text.isNotEmpty())
                {
                    val dialog = newDialogView(ctx)
                    dialog.text.setText("Вернутся без сохранения?")
                    dialog.setPositiveButtonClickListener(){
                        dialog.dismiss()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer, FragmentSaveAddress(),"Save_Address").commit()


                    }
                    dialog.setNegativeButtonClickListener(){
                        dialog.dismiss()
                    }
                    dialog.show()
                }
                else
                {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer, FragmentSaveAddress(),"Save_Address").commit()
                }

            }
            else ->{
                if (fragment !is FragmentMenu) {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        FragmentMenu()
                    ).commit()
                    UnSelect(binding.menuHome)
                }
                else
                {
                   super.onBackPressed();
                }
            }
        }

    }
    
    private fun WorkWithMenuBtn()
    {
       /* textMenuMENU = findViewById(R.id.menu_home)
        textMenuORDER = findViewById(R.id.menu_order)
        textMenuHEART = findViewById(R.id.menu_heart)
        textMenuACCOUNT  = findViewById(R.id.menu_account)
        */

        binding.MenuButton.setOnClickListener {
            UnSelect(binding.menuHome)
            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentMenu()
            ).commit();
        }

        binding.menuOrderButton.setOnClickListener {
            UnSelect(binding.menuOrder)

            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentCart()
            ).commit();
        }

        binding.menuHeartButton.setOnClickListener {
            UnSelect(binding.menuHeart)
            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentFavourites()
            ).commit();
        }

        binding.menuAccountButton.setOnClickListener {
            UnSelect(binding.menuAccount)

            supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentProfile()
            ).commit();
        }
    }


     fun UnSelect(view: View) {

        if(view.visibility != View.VISIBLE)
        {
            binding.menuHome.visibility = View.GONE;

            binding.menuOrder.visibility = View.GONE;

            binding.menuHeart.visibility = View.GONE;

            binding.menuHeart.visibility = View.GONE;

            view.visibility = View.VISIBLE
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_splash_menu));
        }
    }
}