package com.heybeach.profile.domain.data

import com.heybeach.http.HttpParams
import com.heybeach.http.RequestMethod
import com.heybeach.http.executeHttpRequest
import com.heybeach.profile.domain.model.SignupModel
import com.heybeach.profile.domain.model.UserResponse
import kotlinx.coroutines.Deferred

class AuthService {

    fun signUp(email: String, password: String): Deferred<UserResponse> {
        val payload = SignupModel(email, password).toJsonString()
        val params = HttpParams("user/register", RequestMethod.POST, body = payload)
        return executeHttpRequest(params) { stream, headers ->
            val user = parseUser(stream)
            val authToken = headers["x-auth"]!![0]
            UserResponse(user, authToken)
        }
    }
}