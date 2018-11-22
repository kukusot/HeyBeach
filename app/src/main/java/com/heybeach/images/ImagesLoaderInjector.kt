package com.heybeach.images


object ImagesLoaderInjector {

    private val imagesService: ImagesService by lazy {
        ImagesService()
    }

    private val imagesRemoteDataSource: ImagesRemoteDataSource by lazy {
        ImagesRemoteDataSource(imagesService)
    }

    fun inject(imageLoader: ImageLoader) {
        imageLoader.remoteDataSource = imagesRemoteDataSource
    }
}