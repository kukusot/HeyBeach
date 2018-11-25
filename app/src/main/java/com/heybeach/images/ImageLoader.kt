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

    fun loadImage(url: String, imageView: ImageView) {
        recyclingMap[imageView.hashCode()] = url

        if (memoryCache.hasItem(url)) {
            imageView.setImageBitmap(memoryCache.get(url))
        } else {
            imageView.setImageResource(R.drawable.ic_images)
            scope.launch {
                val bitmapResponse = remoteDataSource.fetchImage(url)
                if (bitmapResponse is Response.Success) {
                    val bitmap = bitmapResponse.data
                    memoryCache.cache(url, bitmap)

                    //Ensure that we are displaying the right the viewholder.
                    if (recyclingMap[imageView.hashCode()] == url) {
                        dispatchOnMainThread {
                            imageView.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }


    }


}

