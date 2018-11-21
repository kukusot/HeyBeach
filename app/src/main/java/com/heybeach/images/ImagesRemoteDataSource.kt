package com.heybeach.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.heybeach.http.*

class ImagesRemoteDataSource {

    suspend fun fetchImage(url: String) =
        safeApiCall({ loadImageInternal(url) }, "Error fetching image with url $url")

    private suspend fun loadImageInternal(url: String): Response<Bitmap> {
        val params = HttpParams(url, RequestMethod.GET)
        val imageBitmap = executeHttpRequest(params) {
            BitmapFactory.decodeStream(it)
        }.await()
        return Response.Success(imageBitmap)
    }
}