package com.example.swipeproduct.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.swipeproduct.Utils.UiState
import com.example.swipeproduct.model.ProductListModel
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



}