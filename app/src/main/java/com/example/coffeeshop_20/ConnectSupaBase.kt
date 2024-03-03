package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import com.example.coffeeshop_20.Fragments.FragmentMenu
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Bucket
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import org.json.JSONArray


class ConnectSupaBase {



    private fun client(): SupabaseClient {

        val client = createSupabaseClient(
            supabaseUrl = "https://fnnlozofxzdkvjkzyjzk.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZubmxvem9meHpka3Zqa3p5anprIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTUwNDU3MDAsImV4cCI6MjAxMDYyMTcwMH0.L799DC4l4sGGWoy2B_LR-Yv9jzJzqyjnykvx9FNF_XE"
        ) {
            install(GoTrue)
            install(Postgrest)
            install(Storage)
            //install other modules
        }
        return client;
    }

    suspend fun insertData(){

          //  val city = DataClass.Products(25, "dsad", "dsad", "Dsads", "1", 1, 200);
            //client().postgrest["Coffee"].insert(city)

    }
    suspend fun registor()
   {
       client().gotrue.signUpWith(Email) {
           email = "example@email.com"
           password = "example-password"
       }
    }

    suspend fun signIn()
    {
        client().gotrue.loginWith(Email) {
            email = "example@email.com"
            password = "example-password"
        }
    }

    suspend fun getBucket(): Bucket? {

        return client().storage.retrieveBucketById(bucketId = "Coffee");

    }
    suspend fun getBucketList(): List<Bucket>? {

        // return client().storage.retrieveBucketById(bucketId = "Coffee");

        //https://fnnlozofxzdkvjkzyjzk.supabase.co/storage/v1/object/public/Coffee/1.png


        val bucket = client().storage["Coffee"]

/*        val bucket = client().storage["Coffee"]
        val bytes = bucket.downloadPublic("1.png")*/
/*        client().storage.createBucket(id = "icons") {
            public = true
            fileSizeLimit = 5.megabytes
        }*/
        val bucketret = client().storage.retrieveBucketById(bucketId = "Coffee")
        val ret = client().storage.retrieveBuckets()
        val bucketImageList = client().storage["Coffee"]
        val files = bucketImageList.list()

        Log.e("return", bucketret.toString())
        Log.e("returnList", files.toString())
        return ret

    }




    @SuppressLint("NotifyDataSetChanged")
    suspend fun selectProducts(){

           val coffee = client().postgrest["Products"].select()
           val arrayObject = JSONArray(coffee.body.toString())
           val bucket = client().storage["icons"]


        val imageName ="loading_logo.png"
        val bytes = bucket.downloadPublic(imageName)
        val image: Drawable =  BitmapDrawable(BitmapFactory.decodeByteArray(bytes,0,bytes.size))
           for (i in  0 until  arrayObject.length() ){ //step 1

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
               FragmentMenu.customAdapterProduct.notifyDataSetChanged()
           }
    }

    suspend fun selectImage()
    {
        val coffee = client().postgrest["Products"].select()
        val arrayObject = JSONArray(coffee.body.toString())

        val bucket = client().storage["Coffee"]

        for (i in  0 until  arrayObject.length() ){ //step 1

            val itemObj = arrayObject.getJSONObject(i)
            val imageName = itemObj.getString("image_name")+ ".png"

            val bytes = bucket.downloadPublic(imageName)
            val image: Drawable = BitmapDrawable(BitmapFactory.decodeByteArray(bytes,0,bytes.size))


            TempData.productArray[i].image = image;
            for(i in  0 until  TempData.sortProductArray.size){
                FragmentMenu.customAdapterProduct.notifyItemChanged(i);
            }

            FragmentMenu.customAdapterProduct.notifyItemChanged(i);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    suspend fun selectCategory( ){

        val coffee = client().postgrest["Category"].select()
        val arrayObject = JSONArray(coffee.body.toString())

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