package com.example.swipeproduct.model

data class ProductAddedSuccessfullyModel(
    val message: String,
    val product_details: ProductDetails,
    val product_id: Int,
    val success: Boolean
)