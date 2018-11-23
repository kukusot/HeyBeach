package com.heybeach.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.heybeach.profile.domain.data.AuthService
import com.heybeach.profile.domain.data.UserRemoteDataSource

@SuppressLint("StaticFieldLeak")
object GlobalProvider {

    lateinit var appContext: Context
        private set

    lateinit var preferences: SharedPreferences
        private set

    val authRemoteDataSource: UserRemoteDataSource by lazy {
        UserRemoteDataSource(AuthService())
    }

    fun init(app: App) {
        appContext = app
        preferences = appContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

}