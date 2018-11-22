package com.heybeach.beaches.domain.data

import com.heybeach.beaches.domain.model.Beach
import com.heybeach.http.Response

class BeachesRepository(private val dataSource: BeachesRemoteDataSource) {

    private val beaches = arrayListOf<Beach>()
    private var currentPage = 0

    suspend fun getBeaches(): Response<List<Beach>> {
        val response = dataSource.fetchBooks(currentPage)

        if (response is Response.Success) {
            beaches.addAll(response.data)
            return Response.Success(beaches)
        }
        return response
    }

}