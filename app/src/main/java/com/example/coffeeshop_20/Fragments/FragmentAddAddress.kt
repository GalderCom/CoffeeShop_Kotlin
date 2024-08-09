package com.example.coffeeshop_20.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
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
import android.widget.Toast
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.DataClass
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData
import com.example.coffeeshop_20.databinding.ActivityMainBinding
import com.example.coffeeshop_20.databinding.FragmentAddAddressBinding
import com.example.coffeeshop_20.newDialogView
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
    private var _binding: FragmentAddAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAddressBinding.inflate(inflater, container,false)
         val view = binding.root
        // val view = inflater.inflate(R.layout.fragment_add_address, container, false)

         name = binding.nameText
         house = binding.houseText
         floor = binding.floorText
         entrance = binding.entranceText
         flat =   binding.flatText
         comm = binding.commText
         streetText = binding.streetText

        val idAddress = arguments?.getInt("intValue", 0)

        var  tempItem: DataClass.SaveAddress = DataClass.SaveAddress()
        if(idAddress != null){

            val btnBack: ImageButton = binding.btnBack
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

            val btnDell =  binding.btnDell
            btnDell.visibility = View.VISIBLE;

            btnDell.setOnClickListener(){
                val dialog = newDialogView(view.context)
                dialog.text.setText("Удалить адрес?")
                dialog.setPositiveButtonClickListener(){
                    dialog.dismiss()
                        runBlocking {
                            try {
                                ConnectSupaBase().removeUserAddress(idAddress)
                                Toast.makeText(view.context,"Адрес удален",Toast.LENGTH_SHORT).show()

                                parentFragmentManager.beginTransaction().replace(
                                    R.id.mainFragmentContainer,
                                    FragmentSaveAddress(),"Save_Address").commit();
                            }
                            catch (ex: Exception){
                                Toast.makeText(view.context,"Не возможно удалить!",Toast.LENGTH_SHORT).show()

                            }

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


            val btnSaveAddress = binding.btnSaveAddress
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
            val btnBack: ImageButton = binding.btnBack
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
            val btnSaveAddress = binding.btnSaveAddress
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