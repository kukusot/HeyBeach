package com.heybeach.images


object ImagesLoaderInjector {

    private val imagesService: ImagesService by lazy {
        ImagesService()
    }

    private val imagesRemoteDataSource: ImagesRemoteDataSource by lazy {
        ImagesRemoteDataSource(imagesService)
    }

    private val bitmapMemoryCache: BitmapMemoryCache by lazy {
        BitmapMemoryCache()
    }

    fun inject(imageLoader: ImageLoader) {
        imageLoader.remoteDataSource = imagesRemoteDataSource
        imageLoader.memoryCache = bitmapMemoryCache
    }
}