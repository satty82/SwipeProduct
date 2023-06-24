package com.example.swipeproduct.Utils

import androidx.lifecycle.LiveData
import com.example.swipeproduct.model.ProductListModel


sealed class UiState {
    object Loading : UiState()
    data class Success(
        val productListModel: LiveData<ProductListModel>
    ) : UiState()

    data class Error(val message: String) : UiState()
}