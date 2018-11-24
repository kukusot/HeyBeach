package com.heybeach.beaches.domain.data

import androidx.paging.DataSource
import com.heybeach.beaches.domain.model.Beach

class BeachesDataSourceFactory(val dataSource: BeachesDataSource) : DataSource.Factory<Int, Beach>() {

    override fun create(): DataSource<Int, Beach> = dataSource
}