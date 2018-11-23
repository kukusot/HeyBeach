package com.heybeach.http

import android.util.Log

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Response<T> {
    return try {
        call()
    } catch (e: Throwable) {
        Log.e("API CALL", "Exception ${e.localizedMessage}")
        Response.Error(e)
    }
}
