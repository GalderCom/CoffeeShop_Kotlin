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
import com.example.coffeeshop_20.databinding.FragmentProfileBinding
import com.example.coffeeshop_20.newDialogView
import kotlinx.coroutines.launch

class FragmentProfile : Fragment() {


    private var _binding: FragmentProfileBinding? =  null
    private val binding get()  = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        var view: View? = null

        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        view = binding.root

        ActivityMain.bottomNavigationLayout.visibility = View.VISIBLE;


        val name = binding.nameText
        name.text = "Привет, ${TempData.user.name}!"


        val result = TempData.user.email.substringBefore("@")
        val email = binding.emailText
        email.text = result;


        val myDataClick = binding.frameLayoutMyData
        myDataClick.setOnClickListener() {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentMyData(), "My_Data"
            ).commit();

            ActivityMain.bottomNavigationLayout.visibility = View.GONE;

        }

        val myOrder = binding.textViewMyOrder
        myOrder.setOnClickListener(){

            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentMyOrder(), "My_Order"
            ).commit();

            ActivityMain.bottomNavigationLayout.visibility = View.GONE;

        }

        val saveAddressClick = binding.frameLayoutSaveAddress
        saveAddressClick.setOnClickListener()
        {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer,
                FragmentSaveAddress(), "Save_Address"
            ).commit();

            ActivityMain.bottomNavigationLayout.visibility = View.GONE;

        }

        val btnExit = binding.btnExit
        btnExit.setOnClickListener(){

            val dialog = newDialogView(requireContext())
            dialog.text.setText("Выйти из акаунта?")
            dialog.setPositiveButtonClickListener(){
                lifecycleScope.launch {
                    TempData.user.name = ""
                    TempData.user.email = ""
                    TempData.user.gender = 1
                    TempData.addressArray.clear()
                    TempData.favorArray.clear()
                    TempData.saveAddressArray.clear()
                    TempData.addressArray.clear()

                    ConnectSupaBase().deleteSessionClient()

                    val intent = Intent(view.context, ActivitySplash()::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
            dialog.setNegativeButtonClickListener(){
                dialog.dismiss()
            }
            dialog.show()

        }


        // Inflate the layout for this fragment
        return view
    }
}