package com.heybeach.http

import android.net.Uri
import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import java.io.InputStream
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

const val BASE_URL = "http://techtest.lab1886.io:3000/"

fun createUrlConnection(params: HttpParams): HttpURLConnection {
    val urlString = Uri.parse(BASE_URL + params.path).buildUpon().apply {
        params.queryParams?.forEach { appendQueryParameter(it.key, it.value) }
    }.toString()

    val url = URL(urlString)
    val connection = url.openConnection() as HttpURLConnection
    return connection.apply {
        setRequestProperty("Accept", "application/json")
        setRequestProperty("Content-Type", "application/json")
        requestMethod = params.method.name
        if (params.body != null) {
            this withBody params.body
        }
    }
}

fun <T> executeHttpRequest(params: HttpParams, parse: (stream: InputStream) -> T): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    val urlConnection = createUrlConnection(params)
    val responseCode = urlConnection.responseCode

    if (responseCode < 400) {
        deferred.complete(parse(urlConnection.inputStream))
    } else {
        val errorMessage = urlConnection.errorStream.bufferedReader(Charsets.UTF_8).readText()
        deferred.completeExceptionally(Exception(errorMessage))
    }

    return deferred
}

infix fun HttpURLConnection.withBody(body: String) {
    val writer = OutputStreamWriter(outputStream)
    writer.write(body)
    writer.close()
}