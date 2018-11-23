package com.heybeach.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heybeach.R
import com.heybeach.core.BaseViewModel
import com.heybeach.http.Response
import com.heybeach.profile.domain.data.AuthProvider
import com.heybeach.profile.domain.model.User
import com.heybeach.utils.dispatchOnMainThread
import kotlinx.coroutines.launch

class UserViewModel(private val authProvider: AuthProvider) : BaseViewModel() {

    private val _uiData = MutableLiveData<UserUiModel>()
    val userData: LiveData<UserUiModel>
        get() = _uiData

    init {
        val loggedIn = authProvider.hasUser()
        if (loggedIn) {
            emitUiState(true, loggedIn)
            fetchUser()
        } else {
            emitUiState(false, false)
        }
    }

    fun fetchUser() {
        scope.launch {
            val response = authProvider.fetchUser()
            if (response is Response.Success) {
                dispatchOnMainThread {
                    emitUiState(false, true, response.data)
                }
            } else {
                dispatchOnMainThread {
                    emitUiState(false, authProvider.hasUser(), errorMessage = R.string.error_fetching_data)
                }
            }
        }
    }

    fun logOut() {
        scope.launch {
            authProvider.logOut()
            dispatchOnMainThread {
                emitUiState(false, false)
            }
        }
    }

    fun onLoggedIn(user: User?) {
        emitUiState(false, user != null, user)
    }

    private fun emitUiState(loading: Boolean, loggedIn: Boolean, user: User? = null, errorMessage: Int? = null) {
        _uiData.value = UserUiModel(loading, loggedIn, user, errorMessage)
    }
}
