package com.example.coffeeshop_20.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop_20.Adapters.CustomAdapterFavorites
import com.example.coffeeshop_20.R
import com.example.coffeeshop_20.TempData

@Suppress("UNREACHABLE_CODE")
class FragmentFavourites : Fragment() {

    lateinit var  mRecyclerFavor: RecyclerView;

    companion object{
        lateinit var customAdapterFavorites: CustomAdapterFavorites;
        var valueCount = 0;

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        val countView = view.findViewById<TextView>(R.id.count_coffee_favourites);
        mRecyclerFavor = view.findViewById(R.id.favor_list);
        val label = view.findViewById<TextView>(R.id.nullArray)
        countView.text = valueCount.toString();

        customAdapterFavorites = CustomAdapterFavorites(TempData.favorArray)

        //update
        mRecyclerFavor.adapter = customAdapterFavorites;

        if(valueCount != 0)
        {
            label.visibility = View.GONE;
        }
        else
        {
            label.visibility = View.VISIBLE;
        }

        mRecyclerFavor.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewAttachedToWindow(view: View) {
                countView.text = customAdapterFavorites.itemCount.toString()
                if (customAdapterFavorites.itemCount != 0)
                {
                   label.visibility = View.GONE;
                }
                else
                {
                    label.visibility = View.VISIBLE;
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                countView.text = customAdapterFavorites.itemCount.toString()
                if (customAdapterFavorites.itemCount != 0)
                {
                    label.visibility = View.GONE;
                }
                else
                {
                    label.visibility = View.VISIBLE;
                }
            }
        })

        return view
    }




}