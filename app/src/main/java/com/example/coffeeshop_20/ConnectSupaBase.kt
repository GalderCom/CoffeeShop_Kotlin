package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.coffeeshop_20.Fragments.FragmentFavourites
import com.example.coffeeshop_20.Fragments.FragmentMenu
import com.example.coffeeshop_20.Fragments.FragmentSaveAddress
import com.example.coffeeshop_20.Fragments.FragmentSignUp
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.SessionManager
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
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
import java.net.URL


class ConnectSupaBase {

    var uuid = "";

    fun signUp() {
        runBlocking {
            SbObject.supaBase.auth.signUpWith(OTP) {
                email = TempData.email
                createUser = true
            }
        }
    }

    fun signIn() {
        runBlocking {
            SbObject.supaBase.auth.signInWith(OTP) {
                email = TempData.email
            }
        }
    }

    fun insertUser() {
        runBlocking {
            val user = DataClass.User(uuid, TempData.nameSignUp, TempData.birthdaySignUp, TempData.selectedGender);
            SbObject.client().postgrest["Users"].insert(user)
        }
    }
    suspend fun verifyConfCode(code: String, value: String): Boolean {
        return try {
            when(value)
            {
                "null" ->{
                    SbObject.client().auth.verifyEmailOtp(type = OtpType.Email.MAGIC_LINK, email = TempData.email, token = code)
                    uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id
                    selectUser();
                }
                "singUp"->{
                    SbObject.client().auth.verifyEmailOtp(type = OtpType.Email.MAGIC_LINK, email = TempData.email, token = code)
                    uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id
                    insertUser();
                    selectUser()
                }
            }


            saveSessionClient();

            true
        } catch (e: Exception) {
            val ex = e
            false
        }
    }
    fun updateUser() {
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

    suspend fun selectUser() {
        uuid =  SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id
        TempData.email = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).email.toString()
        val user = SbObject.client().postgrest["Users"].select()
        val arrayObject = JSONArray(user.data)
        for (i in 0 until arrayObject.length()) { //step 1

            val itemObj = arrayObject.getJSONObject(i)

            val id = itemObj.getString("id")
            if (id == uuid) {
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

   suspend fun selectUserAddress() {
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
                if (entrance == "null") {
                    entrance = ""
                }
                var floor = itemObj.getString("floor")
                if (floor == "null") {
                    floor = ""
                }
                var flat = itemObj.getString("flat")
                if (flat == "null") {
                    flat = ""
                }
                var comm = itemObj.getString("comm")
                if (comm == "null") {
                    comm = ""
                }

                val saveAddress =
                    DataClass.SaveAddress(id, street, name, house, entrance, floor, flat, comm)

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

    @SuppressLint("NotifyDataSetChanged")
    suspend fun insertUserAddress(
        street: String,
        name: String,
        house: String,
        enter: String,
        floor: String,
        flat: String,
        comm: String
    ) {

        uuid = SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id;
        val address =
            DataClass.SaveAddressInsert(uuid, street, name, house, enter, floor, flat, comm);
        SbObject.client().postgrest["AdressUsers"].insert(address)
        selectUserAddress();

    }

    suspend fun removeUserAddress(id: Int) {
        for (i in 0 until TempData.saveAddressArray.size) {
            if (id == TempData.saveAddressArray[i].id) {
                val item = TempData.saveAddressArray[i];
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

    fun updateUserAddress(id:Int, street: String, name: String, house: String, enter: String, floor: String, flat: String, comm: String) {
        runBlocking {
            SbObject.client().from("AdressUsers").update(
                {
                    DataClass.SaveAddress::street setTo street
                    DataClass.SaveAddress::name setTo name
                    DataClass.SaveAddress::house setTo house
                    DataClass.SaveAddress::entrance setTo enter
                    DataClass.SaveAddress::floor setTo floor
                    DataClass.SaveAddress::flat setTo flat
                    DataClass.SaveAddress::comm setTo comm
                }
            ) {
                filter {
                    DataClass.SaveAddress::id eq id
                }
            }

            for (i in 0 until TempData.saveAddressArray.size)
            {
                if(id ==   TempData.saveAddressArray[i].id)
                {
                    TempData.saveAddressArray[i].name = name
                    TempData.saveAddressArray[i].comm = comm
                    TempData.saveAddressArray[i].house = house
                    TempData.saveAddressArray[i].entrance = enter
                    TempData.saveAddressArray[i].floor = floor
                    TempData.saveAddressArray[i].flat = flat
                    TempData.saveAddressArray[i].comm = comm
                }
            }
        }
    }

    fun selectFavor() {
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
    fun insertFavor(id_product: Int) {
        GlobalScope.launch {
            if (TempData.favorArray.size != 0) {
                TempData.favorArray.add(
                    DataClass.Favor(
                        TempData.favorArray[TempData.favorArray.lastIndex].id + 1,
                        SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id,
                        id_product
                    )
                )
            }

            val productFvr = DataClass.FavorInsert(SbObject.client().auth.retrieveUserForCurrentSession(updateSession = true).id, id_product);
            SbObject.client().postgrest["FavoritesProduct"].insert(productFvr)
            selectFavor();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeFavor(id_product: Int) {
        GlobalScope.launch {
            for (i in 0 until TempData.favorArray.size) {
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
    suspend fun selectProducts(ctx: Context) {
        if(TempData.productArray.isEmpty())
        {
            val image: Drawable? = ctx.getDrawable(R.drawable.loading_logo);

            val products = SbObject.client().postgrest["Products"].select()
            val arrayObject = JSONArray(products.data)
            if (arrayObject.length() != TempData.productArray.size) {
                TempData.productArray.clear();
                for (i in 0 until arrayObject.length()) { //step 1

                    val itemObj = arrayObject.getJSONObject(i)
                    val id = itemObj.getInt("id")
                    val title = itemObj.getString("title")
                    val description = itemObj.getString("description");
                    val weight = itemObj.getString("weight")
                    val id_category = itemObj.getInt("id_category")
                    val price = itemObj.getInt("price")
                    val imageUri = itemObj.getString("image")

                    val tempItem = DataClass.Products(
                        id,
                        title,
                        description,
                        weight,
                        image,
                        id_category,
                        price,
                        imageUri
                    )
                    TempData.productArray.add(tempItem)

                    if (arrayObject.length() == TempData.productArray.size) {
                        val sortedList = TempData.productArray.sortedBy { it.id }
                        val sortedArrayList = ArrayList<DataClass.Products>(sortedList)
                        TempData.productArray = sortedArrayList;
                    }
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectImage() {
        GlobalScope.launch {
            coroutineScope {
                try {
                    TempData.productArray.forEachIndexed { index, product ->
                        val uri = product.imageUri
                        val drawable =  downloadImage(uri)

                        withContext(Dispatchers.Main) {
                            TempData.productArray[index].image = drawable
                            try {
                                FragmentMenu.customAdapterProduct.notifyDataSetChanged()
                                FragmentFavourites.customAdapterFavorites.notifyDataSetChanged();
                            } catch (ex: Exception) {
                            }
                        }
                    }
                } catch (ex: Exception) { }
            }
        }
    }

    fun downloadImage(url: String): Drawable? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            val imageUrl = URL(url)
            val input = imageUrl.openStream()
            val bitmap = BitmapDrawable.createFromStream(input, "image")
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    @SuppressLint("NotifyDataSetChanged")
    suspend fun selectCategory() {

        if(TempData.categoryArray.isEmpty())
        {
            val category = SbObject.client().postgrest["Category"].select()
            val arrayObject = JSONArray(category.data)

            for (i in 0 until arrayObject.length()) { //step 1

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

    suspend fun selectAddress() {
        if(TempData.addressArray.isEmpty()) {
            val address = SbObject.client().postgrest["AccessibleStreet"].select()
            val arrayObject = JSONArray(address.data)

            for (i in 0 until arrayObject.length()) { //step 1

                val itemObj = arrayObject.getJSONObject(i)
                val title = itemObj.getString("title")
                TempData.addressArray.add(title)
            }
        }
    }

   suspend fun saveSessionClient(){
        val session: UserSession? = SbObject.client().auth.currentSessionOrNull();
        if (session != null) {
        SbObject.supaBase.auth.sessionManager.saveSession(session)
        }
   }

    suspend fun loadSessionClient(): UserInfo? {
        SbObject.client().auth.sessionManager.loadSession()
        return SbObject.client().auth.sessionManager.loadSession()?.user;
    }

    suspend fun deleteSessionClient()
    {
        SbObject.client().auth.sessionManager.deleteSession()
    }
}