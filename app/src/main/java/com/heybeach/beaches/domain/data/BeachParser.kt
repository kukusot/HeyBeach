package com.heybeach.beaches.domain.data

import com.heybeach.beaches.domain.model.Beach
import com.heybeach.http.readResponseAndClose
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

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