package com.example.swipeproduct.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeproduct.Utils.UiState
import com.example.swipeproduct.model.ProductDetailsModel
import com.example.swipeproduct.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : BaseViewModel<UiState>() {

    init {

        viewModelScope.launch (Dispatchers.IO){

            uiState.value = UiState.Loading

            repository.getProductItemsDetails()
        }
    }




}