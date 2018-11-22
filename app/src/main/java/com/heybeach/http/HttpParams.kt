package com.heybeach.http

data class HttpParams(
    val path: String,
    val method: RequestMethod,
    val queryParams: Map<String, String>? = null,
    val body: String? = null
)