package com.example.swipeproduct.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.swipeproduct.api.ServiceBuilder.client
import com.example.swipeproduct.model.ProductListModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.Call
import okhttp3.Callback
import java.io.IOException

class ProductRepository {

    private val productLiveData = MutableLiveData<ProductListModel>()
    val productLD: LiveData<ProductListModel>
        get() = productLiveData
    fun getAPIResponse(){

        // A call can be used once therefore using clone for multiple calls
        client.clone().enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainViewModel", "Response is failure $e")
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {

                if (response.isSuccessful) {

                     val data = response.body!!.string()

                    Log.e("MainViewModel", "Response is data $data")

                    val jsonParser = JsonParser.parseString(data)

                   val modelRepo = productLiveData.postValue( Gson().fromJson(jsonParser, ProductListModel::class.java))

                    Log.e("MainViewModel", "Response is model $modelRepo")


                } else {
                    Log.e("MainViewModel", "Response is not present")


                }

            }

        })
    }


}



