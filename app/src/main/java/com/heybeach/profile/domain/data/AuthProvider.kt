package com.heybeach.profile.domain.data

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.core.content.edit
import com.heybeach.http.Response
import com.heybeach.profile.domain.model.SIGN_UP
import com.heybeach.profile.domain.model.User
import com.heybeach.profile.domain.model.UserResponse

const val AUTH_KEY = "auth_key"

class AuthProvider(private val preferences: SharedPreferences, private val remoteDataSource: UserRemoteDataSource) {

    var user: User? = null
        private set

    @VisibleForTesting
    fun mockUser(user: User) {
        this.user = user
    }

    fun hasUser() = preferences.contains(AUTH_KEY)

    var token: String?
        get() = preferences.getString(AUTH_KEY, "")
        set(value) {
            preferences.edit {
                putString(AUTH_KEY, value)
            }
        }

    suspend fun doAuth(email: String, password: String, action: String): Response<UserResponse> {
        val userResponse = if (action == SIGN_UP) {
            remoteDataSource.signup(email, password)
        } else {
            remoteDataSource.login(email, password)
        }

        if (userResponse is Response.Success) {
            token = userResponse.data.authToken
        }

        return userResponse
    }

    suspend fun fetchUser(): Response<User> {
        return if (user != null) {
            Response.Success(user!!)
        } else {
            fetchRemoteUser()
        }
    }

    private suspend fun fetchRemoteUser() = remoteDataSource.fetchUser(token!!).also {
        if (it is Response.Success) {
            user = it.data
        }
    }

    suspend fun logOut() {
        if (token != null) {
            remoteDataSource.logOut(token!!)
        }
        user = null
        preferences.edit {
            remove(AUTH_KEY)
        }
    }

}