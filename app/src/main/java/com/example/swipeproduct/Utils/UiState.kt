package com.example.swipeproduct.Utils

import com.example.swipeproduct.model.ProductDetailsModel


sealed class UiState {
    object Loading : UiState()
    data class Success(
        val versionFeatures: List<ProductDetailsModel>
    ) : UiState()

    data class Error(val message: String) : UiState()
}