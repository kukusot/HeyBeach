package com.heybeach.profile.di

import com.heybeach.profile.ui.UserFragment
import com.heybeach.profile.ui.UserViewModelFactory

object ProfileInjector {

    private fun provideUserViewModelFactory() = UserViewModelFactory()

    fun inject(fragment: UserFragment) {
        fragment.viewModelFactory = provideUserViewModelFactory()
    }

}