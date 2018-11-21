package com.heybeach.beaches.domain.data

import com.heybeach.beaches.domain.model.Beach
import com.heybeach.http.*

class BeachesRemoteDataSource {

    suspend fun fetchBooks(page: Int) = safeApiCall({ fetchBeachesInternal(page) }, "Unable to fetch beaches")

    private suspend fun fetchBeachesInternal(page: Int): Response<List<Beach>> {
        val params = HttpParams("beaches", RequestMethod.GET, hashMapOf("page" to page.toString()))
        val beaches = executeHttpRequest(params) {
            parseBeachesResponse(stream = it)
        }.await()
        return Response.Success(beaches)
    }
}