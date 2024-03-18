package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import com.example.coffeeshop_20.Fragments.FragmentMenu
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Bucket
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray



class ConnectSupaBase {


    private val supaBase = createSupabaseClient(
        supabaseUrl = "https://fnnlozofxzdkvjkzyjzk.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZubmxvem9meHpka3Zqa3p5anprIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTUwNDU3MDAsImV4cCI6MjAxMDYyMTcwMH0.L799DC4l4sGGWoy2B_LR-Yv9jzJzqyjnykvx9FNF_XE"
    ) {

        install(Auth)
        install(Postgrest)
        install(Storage)
        //install other modules
    }
    private fun client():SupabaseClient{
        return  supaBase;
    }

    suspend fun signUp(email_: String): Email.Result?
    {
       return client().auth.signUpWith(Email) {
            email = email_
            password = "testPassword"
       }
    }

    suspend fun signIn (email_: String):Unit {
        return client().auth.signInWith(Email) {
            email = email_
            password = "testPassword"
        }
    }

    suspend fun insertUser(email:String, name:String, birthday:String) {

        signIn(email)
        val uuid = client().auth.retrieveUserForCurrentSession(updateSession = true).id;
        val user = DataClass.User(uuid,name,birthday);
        client().postgrest["Users"].insert(user)

    }

    val image: Drawable? = TempData.context.getDrawable(R.drawable.loading_logo);
    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    suspend fun selectProducts() {

        val coffee = client().postgrest["Products"].select()
        val arrayObject = JSONArray(coffee.data)
        val bucket = client().storage["icons"]

       // val imageName = "loading_logo.png"
       // val bytes = bucket.downloadPublic(imageName)
        for (i in 0 until arrayObject.length()) { //step 1

            val itemObj = arrayObject.getJSONObject(i)
            val id = itemObj.getInt("id")
            val title = itemObj.getString("title")
            val description = itemObj.getString("description");
            val weight = itemObj.getString("weight")
            val id_category = itemObj.getInt("id_category")
            val price = itemObj.getInt("price")


            val tempItem = DataClass.Products(
                id,
                title,
                description,
                weight,
                image,
                id_category,
                price
            )
            TempData.productArray.add(tempItem)
            //    TempData.imageProduct.add(image)`
            if(arrayObject.length() == TempData.productArray.size)
            {
                //TempData.productArray = TempData.productArray.sortedBy { it.id_category } as ArrayList<DataClass.Products>
                val sortedList = TempData.productArray.sortedBy { it.id }
                val sortedArrayList = ArrayList<DataClass.Products>(sortedList)
                TempData.productArray = sortedArrayList;
            }


        }

    }

    @SuppressLint("NotifyDataSetChanged")
     fun selectImage() {

        val bucket = client().storage["Coffee"]
        // Асинхронный поток
            GlobalScope.launch {
                coroutineScope {
                    TempData.productArray.forEachIndexed { index, product ->
                        val imageName = product.id.toString() + ".png"
                        val bytes = async { bucket.downloadPublic(imageName) }.await()
                        val drawable = BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))

                        withContext(Dispatchers.Main) {
                            TempData.productArray[index].image = drawable
                            FragmentMenu.customAdapterProduct.notifyDataSetChanged()
                        }
                    }
                }
            }

        //Синхронный поток
     /*   runBlocking {
            TempData.productArray.forEachIndexed { index, product ->
                launch {


                    FragmentMenu.customAdapterProduct.notifyDataSetChanged()
                    val imageName = product.id.toString() + ".png"
                    val bytes = bucket.downloadPublic(imageName)
                    val drawable = BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
                    TempData.productArray[index].image = drawable

                }
            }
        }*/


    }


    @SuppressLint("NotifyDataSetChanged")
    suspend fun selectCategory( ){

        val coffee = client().postgrest["Category"].select()
        val arrayObject = JSONArray(coffee.data)

        for (i in  0 until  arrayObject.length() ){ //step 1

            val itemObj = arrayObject.getJSONObject(i)
            val id = itemObj.getInt("id")
            val title = itemObj.getString("title")

            val tempItem = DataClass.Category(
                id,
                title
            )
            TempData.categoryArray.add(tempItem)
            FragmentMenu.customAdapterCategory.notifyDataSetChanged()
        }

    }

}