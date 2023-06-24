package com.example.swipeproduct.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private const val BASE_URL = "https://app.getswipe.in/api/public/"

    fun getInstance(): ProductDetailsService {

        val interceptor =  HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient =
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()


        val gson = GsonBuilder()
            .setLenient()
            .create()


       val retrofit =  Retrofit.Builder()
            .baseUrl(BASE_URL)
             .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

       return retrofit.create(ProductDetailsService::class.java)
    }




}