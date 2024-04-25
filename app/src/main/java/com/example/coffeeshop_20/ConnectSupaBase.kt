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
import io.github.jan.supabase.postgrest.query.Returning
import io.github.jan.supabase.storage.Bucket
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.DelicateCoroutinesApi
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

    fun signIn (email_: String) {
        runBlocking {
            SbObject.client().auth.signInWith(Email) {
                email = email_
                password = "testPassword"
            }
            uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
        }

    }

    suspend fun insertUser(email:String, name:String, birthday:String) {

        signIn(email)
        uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
        val user = DataClass.User(uuid,name,birthday);
        SbObject.client().postgrest["Users"].insert(user)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun insertFavor(id_product:Int)
    {
        GlobalScope.launch {


/*            if (TempData.favorArray.size != 0)
            {
                //TempData.favorArray.add(DataClass.Favor(TempData.favorArray[TempData.favorArray.lastIndex].id+1,uuid,id_product));
                FragmentFavourites.valueCount = TempData.favorArray.size;
            }
            else
            {
                //TempData.favorArray.add(DataClass.Favor(1,uuid,id_product));
                FragmentFavourites.valueCount = TempData.favorArray.size;
            }*/




            uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
            val productFvr = DataClass.FavorInsert(uuid,id_product);
            SbObject.client().postgrest["FavoritesProduct"].insert(productFvr)
            selectFavor();
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeFavor(id_product: Int){
        GlobalScope.launch {
                for(i in 0 until TempData.favorArray.size) {
                    if (id_product == TempData.favorArray[i].id_product) {
                        val item = TempData.favorArray[i];
                       // TempData.favorArray.remove(item);

                        SbObject.client().from("FavoritesProduct").delete {
                            filter {
                                //or
                                eq("id", item.id)
                            }
                        }
                        selectFavor();

                        break;
                    }


                }
        }
    }

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    suspend fun selectProducts() {
        val image: Drawable? = TempData.context.getDrawable(R.drawable.loading_logo);

        val products = SbObject.client().postgrest["Products"].select()
        val arrayObject = JSONArray(products.data)
        if(arrayObject.length() != TempData.productArray.size){
            TempData.productArray.clear();
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


                            try {
                                FragmentMenu.customAdapterProduct.notifyDataSetChanged()
                                FragmentFavourites.customAdapterFavorites.notifyDataSetChanged();
                            }
                            catch (ex : Exception)
                            {

                            }
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

    fun selectFavor()
    {



        GlobalScope.launch(Dispatchers.Main) {
            coroutineScope {
                val favor = SbObject.client().postgrest["FavoritesProduct"].select()
                val arrayObject = JSONArray(favor.data)
                FragmentFavourites.valueCount = arrayObject.length();

                val favorSet = HashSet<Int>()
                val favorToRemove = HashSet<Int>()

                for (i in 0 until arrayObject.length()) {
                    val itemObj = arrayObject.getJSONObject(i)
                    val id = itemObj.getInt("id")
                    val id_user = itemObj.getString("id_user")

                    if (id_user == SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id) {
                        val id_product = itemObj.getInt("id_product")
                        favorSet.add(id)

                        // Check if the item is already in TempData.favorArray
                        val existingItem = TempData.favorArray.find { it.id == id }
                        if (existingItem != null) {
                            // Item already exists
                            if (existingItem.id_product != id_product) {
                                // Update the product ID
                                existingItem.id_product = id_product
                            }
                        } else {
                            // Item does not exist, add it to TempData.favorArray
                            TempData.favorArray.add(DataClass.Favor(id, id_user, id_product))
                        }
                    }
                }

                // Remove items that are no longer in the favor list
                TempData.favorArray.retainAll { favorSet.contains(it.id) }

                // Notify adapter of data set changed
                try {
                    FragmentFavourites.customAdapterFavorites.notifyDataSetChanged()
                } catch (ex: Exception) {
                    // Handle exception
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
        }

    }

}