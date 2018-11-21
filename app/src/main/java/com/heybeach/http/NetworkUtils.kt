package com.heybeach.http

import android.util.Log
import java.io.IOException

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): Response<T> {
    return try {
        call()
    } catch (e: Throwable) {
        Log.e("API CALL", "Exception $e")
        Response.Error(IOException(errorMessage, e))
    }
}
