package com.heybeach.images

import com.heybeach.http.*

class ImagesRemoteDataSource(private val service: ImagesService) {

    suspend fun fetchImage(url: String) = safeApiCall {
        val image = service.fetchImage(url).await()
        Response.Success(image)
    }
}