package com.heybeach.main.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.heybeach.main.ui.MainViewModelFactory
import com.heybeach.main.data.MainModel
import com.heybeach.main.ui.MainActivity
import com.heybeach.main.ui.MainViewModel

object MainActivityInjector {

    private val mainViewModelFactory: ViewModelProvider.Factory by lazy {
        MainViewModelFactory(MainModel())
    }

    fun inject(activity: MainActivity) {
        activity.viewModel = ViewModelProviders.of(activity, mainViewModelFactory).get(MainViewModel::class.java)
    }
}