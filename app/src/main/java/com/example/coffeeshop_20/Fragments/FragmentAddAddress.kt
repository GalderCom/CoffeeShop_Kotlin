package com.example.coffeeshop_20.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Activitys.ActivityMain
import com.example.coffeeshop_20.Adapters.CustomAdapterFavorites
import com.example.coffeeshop_20.Adapters.CustomAdapterSaveAddress
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.example.coffeeshop_20.newDialogView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FragmentAddAddress : Fragment() {


    var selectedItem = ""

    @SuppressLint("StaticFieldLeak")
    companion object{
        lateinit var name: EditText
        lateinit var house: EditText
        lateinit var floor: EditText
        lateinit var entrance : EditText
        lateinit var flat: EditText
        lateinit var comm: EditText
        lateinit var streetText: AutoCompleteTextView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_address, container, false)

         name = view.findViewById(R.id.nameText);
         house = view.findViewById(R.id.houseText)
         floor = view.findViewById(R.id.floorText)
         entrance = view.findViewById(R.id.entranceText)
         flat = view.findViewById(R.id.flatText)
         comm = view.findViewById(R.id.commText)
         streetText = view.findViewById(R.id.streetText)

        val idAddress = arguments?.getInt("intValue", 0)

        var  tempItem: DataClass.SaveAddress = DataClass.SaveAddress()
        if(idAddress != null){

            val btnBack: ImageButton = view.findViewById(R.id.btn_back)
            btnBack.setOnClickListener(){

                if(name.text.isNotEmpty() || comm.text.isNotEmpty() || house.text.isNotEmpty() || floor.text.isNotEmpty() || entrance.text.isNotEmpty() || flat.text.isNotEmpty() || streetText.text.isNotEmpty())
                {
                    val dialog = newDialogView(view.context)
                    dialog.text.setText("Вернутся без сохранения?")
                    dialog.setPositiveButtonClickListener(){
                        dialog.dismiss()
                        parentFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer,
                            FragmentSaveAddress(),"Save_Address").commit();
                    }
                    dialog.setNegativeButtonClickListener(){
                        dialog.dismiss()
                    }
                    dialog.show()
                }


            }

            val btnDell = view.findViewById<ImageView>(R.id.btn_dell)
            btnDell.visibility = View.VISIBLE;

            btnDell.setOnClickListener(){
                val dialog = newDialogView(view.context)
                dialog.text.setText("Удалить адрес?")
                dialog.setPositiveButtonClickListener(){
                    dialog.dismiss()
                        runBlocking {
                            ConnectSupaBase().removeUserAddress(idAddress)
                            Toast.makeText(view.context,"Адрес удален",Toast.LENGTH_SHORT).show()

                            parentFragmentManager.beginTransaction().replace(
                                R.id.mainFragmentContainer,
                                FragmentSaveAddress(),"Save_Address").commit();

                        }

                }
                dialog.setNegativeButtonClickListener(){
                    dialog.dismiss()
                }
                dialog.show()
            }

            for (i in 0 until TempData.saveAddressArray.size)
            {
                if(TempData.saveAddressArray[i].id == idAddress)
                {
                    tempItem = TempData.saveAddressArray[i]
                    break;
                }
            }

            name.setText(tempItem.name)
            streetText.setText(tempItem.street)
            house.setText(tempItem.house)
            floor.setText(tempItem.floor)
            entrance.setText(tempItem.entrance)
            flat.setText(tempItem.flat)
            comm.setText(tempItem.comm)


            val btnSaveAddress = view.findViewById<Button>(R.id.btn_save_address);
            btnSaveAddress.setOnClickListener(){

                if(name.text.isEmpty() || house.text.isEmpty() || streetText.text.isEmpty())
                {
                    Toast.makeText(view.context, "Заполните обязательные поля",Toast.LENGTH_SHORT).show()
                }
                else if(streetText.text.toString() != selectedItem && streetText.text.toString() != tempItem.street) {

                    streetText.error = "Не доставляем на данный адресс";

                }
                else
                {
                    runBlocking {
                        ConnectSupaBase().updateUserAddress(idAddress,streetText.text.toString(),name.text.toString(),house.text.toString(),entrance.text.toString(),floor.text.toString(),flat.text.toString(),comm.text.toString());

                        parentFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer,
                            FragmentSaveAddress(),"Save_Address").commit();

                        Toast.makeText(view.context,"Адрес изменен",Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }
        else
        {
            val btnBack: ImageButton = view.findViewById(R.id.btn_back)
            btnBack.setOnClickListener(){

                val dialog = newDialogView(view.context)
                dialog.text.setText("Вернутся без сохранения?")
                dialog.setPositiveButtonClickListener(){
                    dialog.dismiss()
                    parentFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer,
                        FragmentSaveAddress(),"Save_Address").commit();
                }
                dialog.setNegativeButtonClickListener(){
                    dialog.dismiss()
                }
                dialog.show()

            }

            val adapter = ArrayAdapter(view.context,android.R.layout.simple_dropdown_item_1line,TempData.addressArray);
            streetText.setAdapter(adapter)

            streetText.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                selectedItem = parent.getItemAtPosition(position).toString()
            }

            val btnSaveAddress = view.findViewById<Button>(R.id.btn_save_address);
            btnSaveAddress.setOnClickListener(){

                if(name.text.isEmpty() || house.text.isEmpty() || streetText.text.isEmpty())
                {
                    Toast.makeText(view.context, "Заполните обязательные поля",Toast.LENGTH_SHORT).show()
                }
                else if(streetText.text.toString() != selectedItem) {
                    streetText.error = "Не доставляем на данный адресс";
                }
                else
                {
                    runBlocking {
                        ConnectSupaBase().insertUserAddress(streetText.text.toString(),name.text.toString(),house.text.toString(),entrance.text.toString(),floor.text.toString(),flat.text.toString(),comm.text.toString());

                        parentFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer,
                            FragmentSaveAddress(),"Save_Address").commit();

                        Toast.makeText(view.context,"Адрес сохранен",Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }






        // Inflate the layout for this fragment
        return view;
    }

}