package com.example.swipeproduct.Utils


sealed class UiState {
    object Loading : UiState()
    data class Success(
        val modelClass :Any
    ) : UiState()


    data class Error(val message: String) : UiState()
}