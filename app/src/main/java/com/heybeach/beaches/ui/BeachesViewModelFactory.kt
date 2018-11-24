package com.heybeach.beaches.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heybeach.beaches.domain.data.BeachesDataSourceFactory


@Suppress("UNCHECKED_CAST")
class BeachesViewModelFactory(private val dataSourceFactory: BeachesDataSourceFactory) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = BeachesViewModel(dataSourceFactory) as T
}