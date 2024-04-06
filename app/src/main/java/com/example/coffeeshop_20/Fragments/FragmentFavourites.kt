package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterCategory
import com.example.coffeeshop_20.Adapters.CustomAdapterFavorites
import com.example.coffeeshop_20.Adapters.CustomAdapterProduct
import com.example.coffeeshop_20.ConnectSupaBase
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.SbObject
import com.example.coffeeshop_20.TempData
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class FragmentFavourites : Fragment() {

    lateinit var  mRecyclerFavor: RecyclerView;

    companion object{
        lateinit var customAdapterFavorites: CustomAdapterFavorites;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment__favourites, container, false)
        mRecyclerFavor = view.findViewById<RecyclerView>(R.id.favor_list);



        ConnectSupaBase().selectFavor(view);

        if (TempData.favorArray.size != 0)
        {
            val label = view.findViewById<TextView>(R.id.nullArray)
            val count = view.findViewById<TextView>(R.id.count_coffee_favourites)
            count.text = TempData.favorArray.size.toString();
            label.visibility = View.GONE;
        }


        customAdapterFavorites = CustomAdapterFavorites(TempData.favorArray)

        //update
        mRecyclerFavor.adapter = customAdapterFavorites;

        return view


    }


}