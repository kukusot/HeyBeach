package com.heybeach.images

import android.graphics.Bitmap
import com.heybeach.http.*

class ImagesRemoteDataSource(private val service: ImagesService) {

    suspend fun fetchImage(url: String) = safeApiCall { loadImageInternal(url) }

    private suspend fun loadImageInternal(path: String): Response<Bitmap> {
        val image = service.fetchImage(path).await()
        return Response.Success(image)
    }
}