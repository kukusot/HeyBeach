package com.heybeach.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heybeach.profile.domain.data.AuthProvider

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val authProvider: AuthProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = UserViewModel(authProvider) as T
}