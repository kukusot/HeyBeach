package com.heybeach.profile.di

import androidx.lifecycle.ViewModelProviders
import com.heybeach.app.GlobalProvider
import com.heybeach.profile.domain.data.AuthProvider
import com.heybeach.profile.ui.UserFragment
import com.heybeach.profile.ui.UserViewModel
import com.heybeach.profile.ui.UserViewModelFactory

object UserFragmentInjector {

    private fun provideAuthProvider() = AuthProvider(GlobalProvider.preferences, GlobalProvider.authRemoteDataSource)

    private fun provideUserViewModelFactory() = UserViewModelFactory(provideAuthProvider())

    fun inject(fragment: UserFragment) {
        fragment.viewModel = ViewModelProviders.of(fragment, provideUserViewModelFactory()).get(UserViewModel::class.java)
    }

}