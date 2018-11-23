package com.heybeach.beaches.di

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heybeach.beaches.domain.data.BeachesRemoteDataSource
import com.heybeach.beaches.domain.data.BeachesRepository
import com.heybeach.beaches.domain.data.BeachesService
import com.heybeach.beaches.ui.BeachesAdapter
import com.heybeach.beaches.ui.BeachesFragment
import com.heybeach.beaches.ui.BeachesViewModel
import com.heybeach.beaches.ui.BeachesViewModelFactory

object BeachesFragmentInjector {

    private fun provideViewModelFactory() =
        BeachesViewModelFactory(repository())

    private val beachesService: BeachesService by lazy {
        BeachesService()
    }

    private fun repository() = BeachesRepository(BeachesRemoteDataSource(beachesService))

    fun inject(fragment: BeachesFragment) {
        fragment.viewModel =
                ViewModelProviders.of(fragment, provideViewModelFactory()).get(BeachesViewModel::class.java)
        fragment.beachesAdapter = BeachesAdapter()
        fragment.layoutManager = LinearLayoutManager(fragment.context, RecyclerView.VERTICAL, false)
    }
}