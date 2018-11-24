package com.heybeach.beaches.domain.data

import com.heybeach.http.*

class BeachesRemoteDataSource(private val service: BeachesService) {

    suspend fun fetchBeaches(page: Int) = safeApiCall {
        val beaches = service.getBeaches(page).await()
        Response.Success(beaches)
    }

}