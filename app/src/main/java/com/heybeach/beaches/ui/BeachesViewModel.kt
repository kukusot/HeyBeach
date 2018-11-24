package com.heybeach.beaches.ui

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.heybeach.beaches.domain.data.BeachesDataSourceFactory
import com.heybeach.beaches.domain.model.Beach
import com.heybeach.core.BaseViewModel

class BeachesViewModel(private val dataSourceFactory: BeachesDataSourceFactory) : BaseViewModel() {

    val imagesDataResult: LiveData<PagedList<Beach>> =
        LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()

    val networkState = dataSourceFactory.dataSource.networkState

    override fun onCleared() {
        super.onCleared()
        dataSourceFactory.dataSource.clear()
    }

    fun retry() {
        dataSourceFactory.dataSource.retry()
    }
}

const val PAGE_SIZE = 6
val pagedListConfig = PagedList.Config.Builder().apply {
    setEnablePlaceholders(false)
    setInitialLoadSizeHint(PAGE_SIZE)
    setPageSize(PAGE_SIZE)
}.build()