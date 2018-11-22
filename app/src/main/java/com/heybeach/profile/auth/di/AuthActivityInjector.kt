package com.heybeach.profile.auth.di

import com.heybeach.app.GlobalProvider
import com.heybeach.profile.auth.ui.AuthActivity
import com.heybeach.profile.auth.ui.AuthViewModelFactory
import com.heybeach.profile.domain.data.AuthProvider
import com.heybeach.profile.domain.data.AuthRemoteDataSource
import com.heybeach.profile.domain.data.AuthRepository
import com.heybeach.profile.domain.data.AuthService

object AuthActivityInjector {

    fun inject(activity: AuthActivity) {
        activity.viewModelFactory = provideAuthViewModelFactory()
    }

    private val authRemoteDataSource: AuthRemoteDataSource by lazy {
        AuthRemoteDataSource(AuthService())
    }

    private val authRepository: AuthRepository by lazy {
        AuthRepository(authRemoteDataSource, provideAuthProvider())
    }

    private fun provideAuthProvider() = AuthProvider(GlobalProvider.preferences)

    private fun provideAuthViewModelFactory() = AuthViewModelFactory(authRepository)

}