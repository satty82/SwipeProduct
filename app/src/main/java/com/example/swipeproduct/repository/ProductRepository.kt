package com.example.swipeproduct.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.swipeproduct.api.ServiceBuilder.client
import com.example.swipeproduct.model.AddProductModel
import com.example.swipeproduct.model.ProductListModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException

class ProductRepository {

    private val productLiveData = MutableLiveData<ProductListModel>()
    val productLD: LiveData<ProductListModel>
        get() = productLiveData

    fun getAPIResponse() {

        // A call can be used once therefore using clone for multiple calls
        try {
            client.clone().enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.e("MainViewModel", "Response is failure $e")
                }

                override fun onResponse(call: Call, response: okhttp3.Response) {

                    if (response.isSuccessful) {

                        val data = response.body!!.string()

                        Log.e("MainViewModel", "Response is data $data")

                        val jsonParser = JsonParser.parseString(data)

                        val modelRepo = productLiveData.postValue(
                            Gson().fromJson(
                                jsonParser,
                                ProductListModel::class.java
                            )
                        )

                        Log.e("MainViewModel", "Response is model $modelRepo")


                    } else {
                        Log.e("MainViewModel", "Response is not present")


                    }

                }

            })
        }catch (e:Exception){
            Timber.e("Network request failed $e")
        }
    }

    fun sendDataAPI(addProductModel: AddProductModel) {
        Log.d("Repository", "POST response object is $addProductModel")

      CoroutineScope(Dispatchers.IO).launch {


            // create your json here
            val jsonObject = JSONObject()
            try {
                jsonObject.put("product_name", addProductModel.product_name);
                jsonObject.put("product_type", addProductModel.product_type);
                jsonObject.put("price",        addProductModel.price);
                jsonObject.put("tax",          addProductModel.tax);
                jsonObject.put("files",        addProductModel.image);

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            Log.d("Repository", "POST response JSon is $jsonObject")
            val requestBody = FormBody.Builder()
                .add("product_name", addProductModel.product_name)
                .add("product_type", addProductModel.product_type)
                .add("price",   addProductModel.price)
                .add("tax",  addProductModel.tax)
                .build()

            val client = OkHttpClient()
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val request: Request = Request.Builder()
                .url("https://app.getswipe.in/api/public/add")
                .addHeader("content-type", "multipart/form-data")
                .post(requestBody)
                .build()

            var resStr: String? = null
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
                resStr = response.body!!.string()
                Log.d("Repository", "POST response is $resStr")


            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("Repository", "POST response is error $e")
            }


        }

    }


}



