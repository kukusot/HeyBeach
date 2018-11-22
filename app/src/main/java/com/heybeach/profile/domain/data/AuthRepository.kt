package com.heybeach.profile.domain.data

import com.heybeach.http.Response
import com.heybeach.profile.domain.model.UserResponse

class AuthRepository(private val remoteDataSource: AuthRemoteDataSource, private val authProvider: AuthProvider) {

    suspend fun signup(email: String, password: String): Response<UserResponse> {
        val userResponse = remoteDataSource.signup(email, password)

        if (userResponse is Response.Success) {
            authProvider.token = userResponse.data.authToken
        }

        return userResponse
    }

}