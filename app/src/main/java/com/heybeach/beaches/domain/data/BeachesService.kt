package com.heybeach.beaches.domain.data

import com.heybeach.beaches.domain.model.Beach
import com.heybeach.http.HttpParams
import com.heybeach.http.RequestMethod
import com.heybeach.http.executeHttpRequest
import kotlinx.coroutines.Deferred

class BeachesService {

    fun getBeaches(page: Int): Deferred<List<Beach>> {
        val params = HttpParams("beaches", RequestMethod.GET, hashMapOf("page" to page.toString()))
        return executeHttpRequest(params) { stream, _ ->
            parseBeachesResponse(stream)
        }
    }
}