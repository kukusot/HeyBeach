package com.heybeach.images

import android.widget.ImageView
import com.heybeach.di.GlobalInjector
import com.heybeach.http.Response
import com.heybeach.utils.dispatchOnMainThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object ImageLoader {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val remoteDataSource: ImagesRemoteDataSource = GlobalInjector.imagesRemoteDataSource
    private val recyclingMap = HashMap<Int, String>()

    fun loadImage(url: String, imageView: ImageView) {
        recyclingMap[imageView.hashCode()] = url
        scope.launch(Dispatchers.IO) {
            val bitmapResponse = remoteDataSource.fetchImage(url)
            if (bitmapResponse is Response.Success) {
                if (recyclingMap[imageView.hashCode()] == url) {
                    dispatchOnMainThread {
                        imageView.setImageBitmap(bitmapResponse.data)
                    }
                }
            }
        }
    }

}

