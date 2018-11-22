package com.heybeach.profile.domain.data

import android.content.SharedPreferences
import androidx.core.content.edit

const val AUTH_KEY = "auth_key"

class AuthProvider(private val preferences: SharedPreferences) {

    fun hasUser() = preferences.contains(AUTH_KEY)

    fun logOut() {
        preferences.edit {
            remove(AUTH_KEY)
        }
    }

    var token: String?
        get() = preferences.getString(AUTH_KEY, "")
        set(value) {
            preferences.edit {
                putString(AUTH_KEY, value)
            }
        }

}