package com.heybeach.di

import com.heybeach.images.ImagesRemoteDataSource

object GlobalInjector {

    val imagesRemoteDataSource: ImagesRemoteDataSource by lazy {
        ImagesRemoteDataSource()
    }

}