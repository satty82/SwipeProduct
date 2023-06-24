package com.example.swipeproduct.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.swipeproduct.Utils.UiState
import com.example.swipeproduct.model.AddProductModel
import com.example.swipeproduct.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : BaseViewModel<UiState>() {


    fun callApi() {

        viewModelScope.launch {
            uiState.value = UiState.Loading
            delay(500)
            repository.getAPIResponse()
            uiState.value = UiState.Success(repository.productLD)
        }
    }
    fun sendPostAPI(addProductModel: AddProductModel) {

        viewModelScope.launch {

            uiState.value = UiState.Loading
            repository.sendDataAPI(addProductModel)
            uiState.value = UiState.Success(repository.productLD)
        }

    }



}