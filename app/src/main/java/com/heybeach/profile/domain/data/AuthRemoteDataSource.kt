package com.heybeach.profile.domain.data

import com.heybeach.http.Response
import com.heybeach.http.safeApiCall
import com.heybeach.profile.domain.model.UserResponse

class AuthRemoteDataSource(private val service: AuthService) {

    suspend fun signup(email: String, password: String) = safeApiCall { signupInternal(email, password) }

    private suspend fun signupInternal(email: String, password: String): Response<UserResponse> {
        val user = service.signUp(email, password).await()
        return Response.Success(user)
    }
}