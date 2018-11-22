package com.heybeach.profile.domain.model

import org.json.JSONObject

data class SignupModel(val email: String, val password: String) {

    fun toJsonString(): String {
        return JSONObject().apply {
            put("email", email)
            put("password", password)
        }.toString()
    }
}