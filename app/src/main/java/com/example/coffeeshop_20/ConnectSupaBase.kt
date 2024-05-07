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
import com.example.coffeeshop_20.Fragments.FragmentSaveAddress
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


     fun signUp()
    {
        runBlocking {
            SbObject.supaBase.auth.signUpWith(Email) {
                email = TempData.email
                password = "testPassword"
            }
        }
    }

     fun signIn () {
        runBlocking {
            SbObject.supaBase.auth.signInWith(Email) {
                email = TempData.email
                password = "testPassword"
            }
            uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
            selectUser();
        }
    }

      fun insertUser(name:String, birthday:String) {
        runBlocking {
            signIn()
            uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
            val user = DataClass.User(uuid,name,birthday,TempData.selectedGender);
            SbObject.client().postgrest["Users"].insert(user)
        }
    }

    fun updateUser()
    {
        runBlocking {
            SbObject.client().from("Users").update(
                {
                    DataClass.User::name setTo TempData.user.name
                    DataClass.User::birthday setTo TempData.user.birthday
                    DataClass.User::gender setTo TempData.user.gender
                }
            ) {
                filter {
                    DataClass.User::id eq TempData.user.id
                }
            }
        }
    }

    fun selectUser()
    {
        runBlocking {
            val user = SbObject.client().postgrest["Users"].select()
            val arrayObject = JSONArray(user.data)
            for (i in 0 until arrayObject.length()) { //step 1

                val itemObj = arrayObject.getJSONObject(i)

                val id = itemObj.getString("id")
                if(id == uuid)
                {
                    val name = itemObj.getString("name")
                    val birthday = itemObj.getString("birthday");
                    val gender = itemObj.getInt("gender")

                    val tempItem = DataClass.User(
                        id,
                        name,
                        birthday,
                        gender
                    )
                    TempData.user = tempItem;
                }
            }
        }
    }

    fun selectUserAddress(){
        GlobalScope.launch {
            coroutineScope {
                val address = SbObject.client().postgrest["AdressUsers"].select()
                val arrayObject = JSONArray(address.data)

                val tempSaveAddressArray = mutableListOf<DataClass.SaveAddress>()

                for (i in 0 until arrayObject.length()) {

                    val itemObj = arrayObject.getJSONObject(i)
                    val id = itemObj.getInt("id")
                    val name = itemObj.getString("name")
                    val street = itemObj.getString("street")
                    val house = itemObj.getString("house")

                    var entrance = itemObj.optString("entrance", "")
                    if(entrance == "null")
                    {
                        entrance = ""
                    }
                    var floor = itemObj.getString("floor")
                    if(floor == "null")
                    {
                        floor = ""
                    }
                    var flat = itemObj.getString("flat")
                    if(flat == "null")
                    {
                        flat = ""
                    }
                    var comm = itemObj.getString("comm")
                    if(comm == "null")
                    {
                        comm = ""
                    }

                    val saveAddress = DataClass.SaveAddress(id, street, name, house, entrance, floor, flat, comm)

                    if (!TempData.saveAddressArray.contains(saveAddress)) {
                        TempData.saveAddressArray.add(saveAddress)
                    }
                    tempSaveAddressArray.add(saveAddress)
                }

                TempData.saveAddressArray.retainAll(tempSaveAddressArray)

                try {
                    FragmentSaveAddress.customAdapterAddress.notifyDataSetChanged()
                } catch (ex: Exception) {
                    // Handle exception
                }
            }


        }

    }
    @SuppressLint("NotifyDataSetChanged")
   suspend fun insertUserAddress(street: String, name: String, house: String, enter: String, floor: String, flat: String, comm: String){

        uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
        val address= DataClass.SaveAddressInsert(uuid,street,name,house,enter,floor, flat,comm);
        SbObject.client().postgrest["AdressUsers"].insert(address)
        selectUserAddress();

    }

    suspend fun removeUserAddress(id: Int){
        for(i in 0 until TempData.saveAddressArray.size) {
            if (id == TempData.saveAddressArray[i].id) {
                val item = TempData.saveAddressArray[i];
                // TempData.favorArray.remove(item);

                SbObject.client().from("AdressUsers").delete {
                    filter {
                        //or
                        eq("id", item.id)
                    }
                }
               selectUserAddress()
                break;
            }
        }
    }

    fun selectFavor()
    {
        GlobalScope.launch(Dispatchers.Main) {
            coroutineScope {
                val favor = SbObject.client().postgrest["FavoritesProduct"].select()
                val arrayObject = JSONArray(favor.data)

                val favorSet = HashSet<Int>()

                for (i in 0 until arrayObject.length()) {
                    val itemObj = arrayObject.getJSONObject(i)
                    val id = itemObj.getInt("id")
                    val id_user = itemObj.getString("id_user")

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
    fun insertFavor(id_product:Int)
    {
        if(TempData.favorArray.size != 0)
        {
            TempData.favorArray.add(DataClass.Favor(TempData.favorArray[TempData.favorArray.lastIndex].id+1,uuid,id_product))
        }

        GlobalScope.launch {
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
                    try {
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
                                } catch (ex : Exception){}
                            }
                        }
                    }
                    catch (ex : Exception) {
                    }

            }
        //Синхронный поток
           /* runBlocking {
            TempData.productArray.forEachIndexed { index, product ->
                launch {
                    val bucket = SbObject.client().storage["Coffee"]

                    FragmentMenu.customAdapterProduct.notifyDataSetChanged()
                    val imageName = product.id.toString() + ".png"
                    val bytes = bucket.downloadPublic(imageName)
                    val drawable = BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
                    TempData.productArray[index].image = drawable

                }
            }*/
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
    fun selectAddress()
    {
        GlobalScope.launch {
            val address = SbObject.client().postgrest["AccessibleStreet"].select()
            val arrayObject = JSONArray(address.data)

            for (i in  0 until  arrayObject.length() ){ //step 1

                val itemObj = arrayObject.getJSONObject(i)
                val title = itemObj.getString("title")


                TempData.addressArray.add(title)
            }
        }
    }

}