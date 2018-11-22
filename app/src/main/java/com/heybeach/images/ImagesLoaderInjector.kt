package com.heybeach.images


object ImagesLoaderInjector {

    val imagesRemoteDataSource: ImagesRemoteDataSource by lazy {
        ImagesRemoteDataSource()
    }

    fun inject(imageLoader: ImageLoader) {
        imageLoader.remoteDataSource = imagesRemoteDataSource
    }
}