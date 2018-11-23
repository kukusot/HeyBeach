package com.heybeach.profile.auth.di

import androidx.lifecycle.ViewModelProviders
import com.heybeach.app.GlobalProvider
import com.heybeach.profile.auth.ui.AuthActivity
import com.heybeach.profile.auth.ui.AuthViewModel
import com.heybeach.profile.auth.ui.AuthViewModelFactory
import com.heybeach.profile.domain.data.AuthProvider

object AuthActivityInjector {

    fun inject(activity: AuthActivity) {
        activity.viewModel = ViewModelProviders.of(activity, provideAuthViewModelFactory()).get(AuthViewModel::class.java)
    }

    private fun provideAuthProvider() = AuthProvider(GlobalProvider.preferences, GlobalProvider.authRemoteDataSource)

    private fun provideAuthViewModelFactory() = AuthViewModelFactory(provideAuthProvider())

}