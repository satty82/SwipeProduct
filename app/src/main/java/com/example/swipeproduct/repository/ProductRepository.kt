package com.example.swipeproduct.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.swipeproduct.api.ProductDetailsService
import com.example.swipeproduct.model.ProductDetailsModel

class ProductRepository(private val productDetailsService: ProductDetailsService) {

    private val rocketLiveData = MutableLiveData<ProductDetailsModel>()

    val product: LiveData<ProductDetailsModel>
    get() = rocketLiveData

    suspend fun getProductItemsDetails(){

        val result = productDetailsService.getProductDetails()

        result.body()?.let {

            rocketLiveData.postValue(result.body())

        }
        }

    }
