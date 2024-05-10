package com.example.coffeeshop_20.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.Activitys.ActivitySplash
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import kotlinx.coroutines.launch

class FragmentProfile : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        var view: View? = null
        view = inflater.inflate(R.layout.fragment_profile, container, false)

        ActivityMain.bottomNavigationLayout.visibility = View.VISIBLE;


        val name = view.findViewById<TextView>(R.id.nameText);
        name.text = "Привет, ${TempData.user.name}!"


        val result = TempData.email.substringBefore("@")
        val email = view.findViewById<TextView>(R.id.emailText);
        email.text = result;


        val myDataClick: FrameLayout = view.findViewById(R.id.frameLayout_my_data);
        myDataClick.setOnClickListener() {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentMyData(), "My_Data"
            ).commit();

            ActivityMain.bottomNavigationLayout.visibility = View.GONE;

        }

        val saveAddressClick: FrameLayout = view.findViewById(R.id.frameLayout_save_address)
        saveAddressClick.setOnClickListener()
        {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentSaveAddress(), "Save_Address"
            ).commit();

            ActivityMain.bottomNavigationLayout.visibility = View.GONE;

        }

        val btnExit = view.findViewById<FrameLayout>(R.id.btnExit)
        btnExit.setOnClickListener(){
            lifecycleScope.launch {
                ConnectSupaBase().deleteSessionClient();
                val intent = Intent(view.context,ActivitySplash()::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        // Inflate the layout for this fragment
        return view
    }
}