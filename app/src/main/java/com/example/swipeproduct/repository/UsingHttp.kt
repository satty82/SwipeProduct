package com.example.swipeproduct.repository

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

class UsingHttp {



suspend fun get() {

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://app.getswipe.in/api/public/get")  // Replace with your API URL
        .build()
    var response = client.newCall(request).execute()


    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // Handle network request failure
            Timber.e("Network Request Falied ")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()

                // Process the JSON response as a string
                // For example, log it or pass it to a JSON parser
                Timber.d("Response", responseBody)

                // You can further process the JSON response here
            } else {
                // Handle unsuccessful response
                Timber.e("Network Request Falied ")

            }
        }
    })
}
}