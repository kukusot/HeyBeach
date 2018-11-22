package com.heybeach.main.di

import androidx.lifecycle.ViewModelProvider
import com.heybeach.main.ui.MainViewModelFactory
import com.heybeach.main.data.MainModel
import com.heybeach.main.ui.MainActivity

object MainActivityInjector {

    private val mainViewModelFactory: ViewModelProvider.Factory by lazy {
        MainViewModelFactory(MainModel())
    }

    fun inject(activity: MainActivity) {
        activity.viewModelFactory = mainViewModelFactory
    }
}