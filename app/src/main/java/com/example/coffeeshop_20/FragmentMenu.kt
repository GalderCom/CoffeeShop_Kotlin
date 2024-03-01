package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FragmentMenu : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__menu, container, false)

        init(view);

        //click(view);

        //select category
        lifecycleScope.launch {

            if(TempData.category.isEmpty())
            {
                TempData.category = ConnectSupaBase().selectCategory();
            }
            val customAdapterCategory = CustomAdapterCategory(TempData.category)
            val mRecyclerCategory:RecyclerView = view.findViewById(R.id.category_list)

            //update
            mRecyclerCategory.adapter = customAdapterCategory;
            customAdapterCategory.notifyDataSetChanged();
        }




        return view;
    }

    private fun coffeeOrBakery(view: View)
    {
      /*  val supa = ConnectSupaBase();

        if(AllDataFromBase.coffeeArray.isEmpty())
        {
            lifecycleScope.launch {
                customAdapterMenu = CustomAdaptersCoffee(supa.getDataCoffee())
                AllDataFromBase.coffeeArray = supa.getDataCoffee();
                sortingCoffee(view)
            }
        }
        else
        {
            sortingCoffee(view)
        }

        if(AllDataFromBase.bakeryArray.isEmpty())
        {
            lifecycleScope.launch {
                customAdapterBakery = CustomAdapterBakery(supa.getDataBakery())
                AllDataFromBase.bakeryArray = supa.getDataBakery();
                sortingCoffee(view)
            }
        }
        else
        {
            sortingCoffee(view)
        }*/
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun sortingCoffee(view: View)
    {
      /*  val arrayCoffee = ArrayList<DataClass.Coffee>()

        val mRecyclerView:RecyclerView = view.findViewById(R.id.listMenu1)

        val customAdapter = CustomAdaptersCoffee(arrayCoffee)
        mRecyclerView.adapter = customAdapter;

        for (i in 0 until AllDataFromBase.coffeeArray.size) {
            if (AllDataFromBase.coffeeArray[i].category_id == selectCategory) {
                arrayCoffee.add(AllDataFromBase.coffeeArray[i])
            }
            customAdapter.notifyDataSetChanged()
            mRecyclerView.isNotEmpty()
        }*/

    }



    @SuppressLint("NotifyDataSetChanged")
    private fun sortingBakery(view: View)
    {
       /* val arrayBakery = ArrayList<DataClass.Bakery>()

        val mRecyclerView:RecyclerView = view.findViewById(R.id.listMenu1)

        val customAdapter = CustomAdapterBakery(arrayBakery)
        mRecyclerView.adapter = customAdapter;

        for (i in 0 until AllDataFromBase.bakeryArray.size) {

            arrayBakery.add(AllDataFromBase.bakeryArray[i])
            customAdapter.notifyDataSetChanged()
            mRecyclerView.isNotEmpty()
        }*/
    }


    private fun init(view: View){

       /* typeClassic = view.findViewById(R.id.classic_type_coffee);
        typeAutor = view.findViewById(R.id.autor_type_coffee);
        typeRaf = view.findViewById(R.id.raf_type_coffee);
        typeCold = view.findViewById(R.id.cold_type_coffee);

        typeTextClassic = view.findViewById(R.id.classic_type_text_coffee);
        typeTextAutor = view.findViewById(R.id.autor_type_text_coffee);
        typeTextRaf = view.findViewById(R.id.raf_type_text_coffee);
        typeTextCold = view.findViewById(R.id.cold_type_text_coffee);
        containerCategory = view.findViewById(R.id.containerCategory);*/

    }
    lateinit var containerCategory: LinearLayout;
    private fun click(view: View)
    {



    }

}