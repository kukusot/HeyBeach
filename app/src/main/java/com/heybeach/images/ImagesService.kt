package com.heybeach.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.heybeach.http.HttpParams
import com.heybeach.http.RequestMethod
import com.heybeach.http.executeHttpRequest
import kotlinx.coroutines.Deferred

class ImagesService {

    fun fetchImage(path: String): Deferred<Bitmap> {
        val params = HttpParams(path, RequestMethod.GET)
        return executeHttpRequest(params) { stream, _ ->
            BitmapFactory.decodeStream(stream)
        }

    }
}