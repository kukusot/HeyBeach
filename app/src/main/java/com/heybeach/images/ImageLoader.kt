package com.heybeach.images

import android.widget.ImageView
import com.heybeach.R
import com.heybeach.app.GlobalProvider
import com.heybeach.http.Response
import com.heybeach.utils.dispatchOnMainThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


object ImageLoader {

    private val scope = CoroutineScope(GlobalProvider.io)
    private val recyclingMap = HashMap<Int, String>()

    lateinit var remoteDataSource: ImagesRemoteDataSource
    lateinit var memoryCache: BitmapMemoryCache

    init {
        ImagesLoaderInjector.inject(this)
    }

    fun loadImage(path: String?, imageView: ImageView) {
        if (path == null) {
            imageView.setImageBitmap(null)
            return
        }
        recyclingMap[imageView.hashCode()] = path

        if (memoryCache.hasItem(path)) {
            imageView.setImageBitmap(memoryCache.get(path))
        } else {
            imageView.setImageResource(R.drawable.ic_images)
            loadImageFromNetwork(path, imageView)
        }
    }

    private fun loadImageFromNetwork(path: String, imageView: ImageView) {
        scope.launch {
            val bitmapResponse = remoteDataSource.fetchImage(path)
            if (bitmapResponse is Response.Success) {
                val bitmap = bitmapResponse.data
                memoryCache.cache(path, bitmap)

                //Ensure that we are displaying the right the viewholder.
                if (recyclingMap[imageView.hashCode()] == path) {
                    dispatchOnMainThread {
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }


}

