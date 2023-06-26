package com.example.swipeproduct.viewmodels

import NetworkLiveData
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.swipeproduct.Utils.UiState
import com.example.swipeproduct.model.AddProductModel
import com.example.swipeproduct.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(context: Context, private val repository: ProductRepository) : BaseViewModel<UiState>() {

    private val networkLiveData: NetworkLiveData = NetworkLiveData(context)


    fun observeNetworkConnectivity(owner: LifecycleOwner, observer: (Boolean) -> Unit) {
        networkLiveData.observe(owner) { isConnected ->
            if (isConnected == true) {
                // Perform network-related operations
                observer(isConnected)
            } else {
                // Handle no network connection
                if (isConnected != null) {
                    observer(isConnected)
                }
            }
        }
    }

    fun callApi() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            repository.getAPIResponse()
            uiState.value = UiState.Success(repository.productLD)
        }
    }
    fun sendPostAPI(addProductModel: AddProductModel) {

        uiState.value = UiState.Loading

        viewModelScope.launch {

            val response = repository.sendDataAPI(addProductModel)
            Log.d("ViewModel","viewmodel sendPostAPI is $response")
            uiState.value = UiState.Success(response)
        }

    }



}