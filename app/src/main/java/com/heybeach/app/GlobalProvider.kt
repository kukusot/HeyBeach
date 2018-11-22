package com.heybeach.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("StaticFieldLeak")
object GlobalProvider {

    lateinit var appContext: Context
        private set

    lateinit var preferences: SharedPreferences
        private set

    fun init(app: App) {
        appContext = app
        preferences = appContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

}