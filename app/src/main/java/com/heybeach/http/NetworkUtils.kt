package com.heybeach.http

import android.net.Uri
import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import java.io.IOException
import java.io.InputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

const val BASE_URL = "http://techtest.lab1886.io:3000/"
const val TIMEOUT = 5000

fun <T> executeHttpRequest(
    params: HttpParams,
    parse: (stream: InputStream, headers: Map<String, List<String>>) -> T
): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    val urlConnection = createUrlConnection(params)
    val responseCode = urlConnection.responseCode

    if (responseCode < 400) {
        deferred.complete(parse(urlConnection.inputStream, urlConnection.headerFields))
        urlConnection.inputStream.close()
    } else {
        val errorMessage = urlConnection.errorStream.bufferedReader(Charsets.UTF_8).readText()
        urlConnection.errorStream.close()
        deferred.completeExceptionally(IOException(errorMessage))
    }

    urlConnection.disconnect()

    return deferred
}

fun createUrlConnection(params: HttpParams): HttpURLConnection {
    val urlString = Uri.parse(BASE_URL + params.path).buildUpon().apply {
        params.queryParams?.forEach { appendQueryParameter(it.key, it.value) }
    }.toString()

    val url = URL(urlString)
    val connection = url.openConnection() as HttpURLConnection
    return connection.apply {
        setRequestProperty("Accept", "application/json")
        setRequestProperty("Content-Type", "application/json")
        setRequestProperty("Cache-Control", "no-cache")
        params.headers?.forEach {
            setRequestProperty(it.key, it.value)
        }

        readTimeout = TIMEOUT
        connectTimeout = TIMEOUT
        doInput = true
        requestMethod = params.method.name
        if (params.body != null) {
            this withBody params.body
        }
    }
}

infix fun HttpURLConnection.withBody(body: String) {
    val writer = OutputStreamWriter(outputStream)
    writer.write(body)
    writer.close()
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Response<T> {
    return try {
        call()
    } catch (e: Throwable) {
        Log.e("API CALL", "Exception ${e.localizedMessage}")
        Response.Error(e)
    }
}
