package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.coffeeshop_20.Fragments.FragmentFavourites
import com.example.coffeeshop_20.Fragments.FragmentMenu
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
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

    var uuid = ""

    suspend fun signUp(email_: String): Email.Result?
    {
       return SbObject.client().auth.signUpWith(Email) {
            email = email_
            password = "testPassword"
       }
    }

    suspend fun signIn (email_: String) {
         SbObject.client().auth.signInWith(Email) {
             email = email_
             password = "testPassword"
         }
        uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
    }

    suspend fun insertUser(email:String, name:String, birthday:String) {

        signIn(email)
        uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
        val user = DataClass.User(uuid,name,birthday);
        SbObject.client().postgrest["Users"].insert(user)

    }

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    suspend fun selectProducts() {
        val image: Drawable? = TempData.context.getDrawable(R.drawable.loading_logo);

        val products = SbObject.client().postgrest["Products"].select()
        val arrayObject = JSONArray(products.data)

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

            if(arrayObject.length() == TempData.productArray.size)
            {
                val sortedList = TempData.productArray.sortedBy { it.id }
                val sortedArrayList = ArrayList<DataClass.Products>(sortedList)
                TempData.productArray = sortedArrayList;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
     fun selectImage() {


        // Асинхронный поток
            GlobalScope.launch {
                coroutineScope {
                    val bucket = SbObject.client().storage["Coffee"]
                    TempData.productArray.forEachIndexed { index, product ->
                        val imageName = product.id.toString() + ".png"
                        val bytes = async { bucket.downloadPublic(imageName) }.await()
                        val drawable = BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))

                        withContext(Dispatchers.Main) {
                            TempData.productArray[index].image = drawable
                            FragmentMenu.customAdapterProduct.notifyDataSetChanged()
                            //FragmentFavourites.customAdapterFavorites.notifyDataSetChanged();
                        }
                    }
                }
            }

        //Синхронный поток
/*        runBlocking {
            TempData.productArray.forEachIndexed { index, product ->
                launch {
                    val bucket = SbObject.client().storage["Coffee"]

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
     fun selectFavor(view : View)
    {

        GlobalScope.launch(Dispatchers.Main) {
            coroutineScope{

                var finish = true;
                val favor = SbObject.client().postgrest["FavoritesProduct"].select()
                val arrayObject = JSONArray(favor.data)

                if (arrayObject.length() != TempData.favorArray.size && finish == true)
                {
                    finish = false;
                    TempData.favorArray.clear();
                    for (i in  0 until  arrayObject.length() ){

                        val itemObj = arrayObject.getJSONObject(i)
                        val id = itemObj.getInt("id")
                        val id_user = itemObj.getString("id_user")

                        if (id_user == SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id)
                        {
                            val id_product = itemObj.getInt("id_product")
                            val tempItem = DataClass.Favor(
                                id,
                                id_product
                            )
                            TempData.favorArray.add(tempItem);


                        }

                    }

                    if (TempData.favorArray.size != 0)
                    {
                        val label = view.findViewById<TextView>(R.id.nullArray)
                        val count = view.findViewById<TextView>(R.id.count_coffee_favourites)
                        count.text = TempData.favorArray.size.toString();
                        label.visibility = View.GONE;
                    }
                    withContext(Dispatchers.Main) {
                        FragmentFavourites.customAdapterFavorites.notifyDataSetChanged();
                    }
                    finish = true
                }
            }
        }



    }

    @SuppressLint("NotifyDataSetChanged")
    suspend fun selectCategory( ){

        val category = SbObject.client().postgrest["Category"].select()
        val arrayObject = JSONArray(category.data)

        for (i in  0 until  arrayObject.length() ){ //step 1

            val itemObj = arrayObject.getJSONObject(i)
            val id = itemObj.getInt("id")
            val title = itemObj.getString("title")

            val tempItem = DataClass.Category(
                id,
                title
            )

            TempData.categoryArray.add(tempItem)
          //  FragmentMenu.customAdapterCategory.notifyDataSetChanged()
        }

    }

}