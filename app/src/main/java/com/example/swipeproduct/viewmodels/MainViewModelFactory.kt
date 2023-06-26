package com.example.swipeproduct.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swipeproduct.repository.ProductRepository

class MainViewModelFactory(private val context: Context, private val repository: ProductRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context,repository) as T
    }
}