package com.heybeach.profile.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heybeach.profile.domain.data.AuthRepository

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = AuthViewModel(repository) as T
}