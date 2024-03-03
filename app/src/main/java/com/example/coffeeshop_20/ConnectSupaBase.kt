package com.example.coffeeshop_20

import android.util.Log
import org.json.JSONArray
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

            val city = DataClass.Products(25, "dsad", "dsad", "Dsads", "1", 1, 200);
            client().postgrest["Coffee"].insert(city)

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



    suspend fun selectProducts(): ArrayList<DataClass.Products>{

        val arrayList:  ArrayList<DataClass.Products> = ArrayList()

           val coffee = client().postgrest["Products"].select()
           val arrayObject = JSONArray(coffee.body.toString())

           for (i in  0 until  arrayObject.length() ){ //step 1

               val itemObj = arrayObject.getJSONObject(i)
               val id = itemObj.getInt("id")
               val title = itemObj.getString("title")
               val description = itemObj.getString("description");
               val weight = itemObj.getString("weight")
               val image = itemObj.getString("image_name")
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
               arrayList.add(tempItem);
           }

        return arrayList;
    }

    suspend fun selectCategory( ): ArrayList<DataClass.Category>{

        val arrayList:  ArrayList<DataClass.Category> = ArrayList()

        val coffee = client().postgrest["Category"].select()
        val arrayObject = JSONArray(coffee.body.toString())

        for (i in  0 until  arrayObject.length() ){ //step 1

            val itemObj = arrayObject.getJSONObject(i)
            val id = itemObj.getInt("id")
            val title = itemObj.getString("title")

            val tempItem = DataClass.Category(id, title)
            arrayList.add(tempItem);
        }

        return arrayList;
    }

}