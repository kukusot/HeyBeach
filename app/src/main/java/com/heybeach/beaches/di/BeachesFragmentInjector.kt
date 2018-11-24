package com.heybeach.beaches.di

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heybeach.beaches.domain.data.*
import com.heybeach.beaches.ui.BeachesAdapter
import com.heybeach.beaches.ui.BeachesFragment
import com.heybeach.beaches.ui.BeachesViewModel
import com.heybeach.beaches.ui.BeachesViewModelFactory

object BeachesFragmentInjector {

    private val beachesService: BeachesService by lazy {
        BeachesService()
    }

    private val beachesRemoteDataSource: BeachesRemoteDataSource by lazy {
        BeachesRemoteDataSource(beachesService)
    }

    private fun provideViewModelFactory() =
        BeachesViewModelFactory(dataSourceFactory())

    private fun dataSourceFactory() = BeachesDataSourceFactory(BeachesDataSource(beachesRemoteDataSource))

    fun inject(fragment: BeachesFragment) {
        fragment.viewModel =
                ViewModelProviders.of(fragment, provideViewModelFactory()).get(BeachesViewModel::class.java)
        fragment.beachesAdapter = BeachesAdapter()
        fragment.layoutManager = LinearLayoutManager(fragment.context, RecyclerView.VERTICAL, false)
    }
}