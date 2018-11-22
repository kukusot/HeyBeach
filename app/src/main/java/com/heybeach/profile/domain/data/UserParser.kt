package com.heybeach.profile.domain.data

import com.heybeach.http.readResponseAndClose
import com.heybeach.profile.domain.model.User
import org.json.JSONObject
import java.io.InputStream

fun parseUser(stream: InputStream): User {
    val responseString = stream.readResponseAndClose()
    val jsonObject = JSONObject(responseString)
    return User(jsonObject.optString("_id"), jsonObject.optString("email"))
}