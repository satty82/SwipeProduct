package com.example.swipeproduct.koin

import androidx.lifecycle.ViewModelProvider
import com.example.swipeproduct.repository.ProductRepository
import com.example.swipeproduct.viewmodels.MainViewModel
import com.example.swipeproduct.viewmodels.MainViewModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule =module{

    single{ProductRepository()}
    viewModel { MainViewModel(get(),get()) }

    factory<ViewModelProvider.Factory> { MainViewModelFactory(get(),get()) }
}