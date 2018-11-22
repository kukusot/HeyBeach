package com.heybeach.beaches.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heybeach.beaches.domain.data.BeachesRepository


@Suppress("UNCHECKED_CAST")
class BeachesViewModelFactory(private val repository: BeachesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = BeachesViewModel(repository) as T
}