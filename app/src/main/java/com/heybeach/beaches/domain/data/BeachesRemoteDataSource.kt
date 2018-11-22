package com.heybeach.beaches.domain.data

import com.heybeach.beaches.domain.model.Beach
import com.heybeach.http.*

class BeachesRemoteDataSource(private val service: BeachesService) {

    suspend fun fetchBooks(page: Int) = safeApiCall({ fetchBeachesInternal(page) }, "Unable to fetch beaches")

    private suspend fun fetchBeachesInternal(page: Int): Response<List<Beach>> {
        val books = service.getBeaches(page).await()
        return Response.Success(books)
    }
}