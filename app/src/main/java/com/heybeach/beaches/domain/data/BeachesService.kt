package com.heybeach.beaches.domain.data

import com.heybeach.beaches.domain.model.Beach
import com.heybeach.http.HttpParams
import com.heybeach.http.RequestMethod
import com.heybeach.http.executeHttpRequest
import com.heybeach.http.readResponseAndClose
import kotlinx.coroutines.Deferred
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class BeachesService {

    fun getBeaches(page: Int): Deferred<List<Beach>> {
        val params = HttpParams("beaches", RequestMethod.GET, hashMapOf("page" to page.toString()))
        return executeHttpRequest(params) { stream, _ ->
            parseBeachesResponse(stream)
        }
    }
}

fun parseBeachesResponse(stream: InputStream): List<Beach> {
    val responseString = stream.readResponseAndClose()
    val beachesArray = JSONArray(responseString)
    val beaches = arrayListOf<Beach>()


    (0 until beachesArray.length()).map {
        val jsonObject = beachesArray.getJSONObject(it)
        beaches.add(createBeachFromJsonObject(jsonObject = jsonObject))
    }
    return beaches
}

private fun createBeachFromJsonObject(jsonObject: JSONObject): Beach {
    jsonObject.run {
        val id = optString("_id")
        val name = optString("name")
        val url = optString("url")
        val width = optInt("width")
        val height = optInt("height")
        return Beach(id, name, url, width, height)
    }
}