package com.heybeach.beaches.di

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heybeach.beaches.domain.data.BeachesRemoteDataSource
import com.heybeach.beaches.domain.data.BeachesRepository
import com.heybeach.beaches.ui.BeachesAdapter
import com.heybeach.beaches.ui.BeachesFragment
import com.heybeach.beaches.ui.BeachesViewModelFactory

object BeachesFragmentInjector {

    private val beachesViewModelFactory: BeachesViewModelFactory by lazy {
        BeachesViewModelFactory(repository())
    }

    private fun repository() = BeachesRepository(BeachesRemoteDataSource())

    fun inject(fragment: BeachesFragment) {
        fragment.beachesViewModelFactory = beachesViewModelFactory
        fragment.beachesAdapter = BeachesAdapter()
        fragment.layoutManager = LinearLayoutManager(fragment.context, RecyclerView.VERTICAL, false)
    }
}