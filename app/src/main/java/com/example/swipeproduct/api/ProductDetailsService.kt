package com.example.swipeproduct.api

import com.example.swipeproduct.model.ObjectProduct
import com.example.swipeproduct.model.ProductListModel
import okhttp3.Call
import okhttp3.Callback
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import java.util.Objects

interface ProductDetailsService {

    @GET("/get")
     fun getProductDetails() : retrofit2.Call<ProductListModel>
}