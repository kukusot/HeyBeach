package com.heybeach.http

class HttpParams(val path: String, val method: RequestMethod, val queryParams: Map<String, String>? = null)