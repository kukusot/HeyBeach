package com.heybeach.profile.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heybeach.profile.domain.data.AuthProvider

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val authProvider: AuthProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = AuthViewModel(authProvider) as T
}