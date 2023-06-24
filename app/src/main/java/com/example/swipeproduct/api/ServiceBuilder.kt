package com.example.swipeproduct.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceBuilder {

    val okhttp = OkHttpClient()
    val url = "https://app.getswipe.in/api/public/get"
    val request = Request.Builder().url(url).build()

    val client =  okhttp.newCall(request)



}