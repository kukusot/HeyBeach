package com.heybeach.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heybeach.main.data.MainModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val mainModel: MainModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = MainViewModel(mainModel) as T
}