package com.example.coffeeshop_20

import android.widget.ArrayAdapter
import android.widget.ListView
import org.json.JSONArray
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConnectSupaBase {



    private fun client(): SupabaseClient {

        val client = createSupabaseClient(
            supabaseUrl = "https://fnnlozofxzdkvjkzyjzk.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZubmxvem9meHpka3Zqa3p5anprIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTUwNDU3MDAsImV4cCI6MjAxMDYyMTcwMH0.L799DC4l4sGGWoy2B_LR-Yv9jzJzqyjnykvx9FNF_XE"
        ) {
            install(GoTrue)
            install(Postgrest)
            //install other modules
        }

        return client;
    }

   /* suspend fun insertData(){

            val city = Test(name = "The Shire")
            client().postgrest["test"].insert(city)

    }*/



    suspend fun getDataCoffee( ): ArrayList<DataClass.Coffee>{

        val arrayList:  ArrayList<DataClass.Coffee> = ArrayList()

           val coffee = client().postgrest["Coffee"].select()
           val arrayObject = JSONArray(coffee.body.toString())

           for (i in  0 until  arrayObject.length() ){ //step 1

               val itemObj = arrayObject.getJSONObject(i)
               val id = itemObj.getInt("id")
               val name = itemObj.getString("name")
               val description = itemObj.getString("description");
               val shortDescription = itemObj.getString("short_description")
               val image = itemObj.getInt("image")
               val categoryID = itemObj.getInt("category_id")
               val price = itemObj.getInt("price")

               val tempItem = DataClass.Coffee(id,name,description,shortDescription,image,categoryID,price)
               arrayList.add(tempItem);
             //  customAdapter.notifyDataSetChanged()
           }

        return arrayList;
    }

}