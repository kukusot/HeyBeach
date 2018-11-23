package com.heybeach.profile.domain.data

import com.heybeach.http.Response
import com.heybeach.http.safeApiCall
import com.heybeach.profile.domain.model.UserResponse

class UserRemoteDataSource(private val service: AuthService) {

    suspend fun signup(email: String, password: String) = safeApiCall {
        val user = service.signUp(email, password).await()
        Response.Success(user)
    }

    suspend fun login(email: String, password: String) = safeApiCall {
        val user = service.login(email, password).await()
        Response.Success(user)
    }

    suspend fun logOut(authToken: String) = safeApiCall {
        val response = service.logOut(authToken).await()
        Response.Success(response)
    }

    suspend fun fetchUser(authToken: String) = safeApiCall {
        val response = service.fetchUser(authToken).await()
        Response.Success(response)
    }

}