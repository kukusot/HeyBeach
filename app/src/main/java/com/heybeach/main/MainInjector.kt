package com.heybeach.main

import androidx.lifecycle.ViewModelProvider
import com.heybeach.main.data.MainModel

object MainInjector {

    private val mainModel: MainModel by lazy {
        MainModel()
    }

    val mainViewModelFactory: ViewModelProvider.Factory by lazy {
        MainViewModelFactory(mainModel)
    }
}