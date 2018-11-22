package com.heybeach.profile.auth.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heybeach.core.BaseViewModel
import com.heybeach.http.NetworkState
import com.heybeach.profile.domain.data.AuthRepository
import com.heybeach.profile.domain.data.validateForm
import com.heybeach.profile.domain.model.SignupModel
import com.heybeach.profile.domain.model.ValidationError
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : BaseViewModel() {

    lateinit var action: String

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _formData = MutableLiveData<List<ValidationError>>()
    val formData: LiveData<List<ValidationError>>
        get() = _formData


    fun signup(data: SignupModel) {
        val validationResult = validateForm(data)
        _formData.value = validationResult
        if (validationResult.isEmpty()) {
            executeSignup(data)
        }
    }

    private fun executeSignup(data: SignupModel) {
        scope.launch {
            val response = repository.signup(data.email, data.password)
            Log.e("fiko", "sig res " + response)
        }
    }
}