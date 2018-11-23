package com.heybeach.profile.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heybeach.core.BaseViewModel
import com.heybeach.http.Response
import com.heybeach.profile.domain.data.AuthProvider
import com.heybeach.profile.domain.model.User
import kotlinx.coroutines.launch

class UserViewModel(private val authProvider: AuthProvider) : BaseViewModel() {

    private val _uiData = MutableLiveData<UserUiModel>()
    val userData: LiveData<UserUiModel>
        get() = _uiData

    init {
        if (authProvider.hasUser()) {
            fetchUser()
        } else {
            emitUiState(false)
        }
    }

    private fun fetchUser() {
        scope.launch {
            val response = authProvider.fetchUser()
            Log.e("fikokurva", "res " + response)
            if (response is Response.Success) {
            }
        }
    }

    private fun emitUiState(loading: Boolean, user: User? = null) {
        _uiData.value = UserUiModel(loading, user)
    }
}