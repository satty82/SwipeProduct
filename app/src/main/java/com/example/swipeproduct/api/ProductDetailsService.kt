package com.example.swipeproduct.api

import com.example.swipeproduct.model.ProductDetailsModel
import retrofit2.Response
import retrofit2.http.GET

interface ProductDetailsService {

    @GET("/get")
    suspend fun getProductDetails() : Response<ProductDetailsModel>
}