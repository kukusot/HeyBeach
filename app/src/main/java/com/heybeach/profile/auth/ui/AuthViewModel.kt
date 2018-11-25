package com.heybeach.profile.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heybeach.R
import com.heybeach.core.BaseViewModel
import com.heybeach.http.Response
import com.heybeach.profile.domain.data.AuthProvider
import com.heybeach.profile.domain.data.validateForm
import com.heybeach.profile.domain.model.SIGN_UP
import com.heybeach.profile.domain.model.SignupModel
import com.heybeach.profile.domain.model.User
import com.heybeach.profile.domain.model.ValidationError
import com.heybeach.utils.dispatchOnMainThread
import kotlinx.coroutines.launch

class AuthViewModel(private val authProvider: AuthProvider) : BaseViewModel() {

    lateinit var action: String

    private val _uiState = MutableLiveData<AuthUiModel>()
    val uiState: LiveData<AuthUiModel>
        get() = _uiState

    private val _formData = MutableLiveData<List<ValidationError>>()
    val formData: LiveData<List<ValidationError>>
        get() = _formData

    fun validateAndAuth(data: SignupModel) {
        val validationResult = validateForm(data)
        _formData.value = validationResult

        if (validationResult.isEmpty()) {
            executeAuth(data)
        }
    }

    private fun executeAuth(data: SignupModel) {
        scope.launch {
            emitUiState(true)
            val response = authProvider.doAuth(data.email, data.password, action)

            if (response is Response.Success) {
                emitUiState(false, user = response.data.user)
            } else {
                val errorResId = if (action == SIGN_UP) R.string.error_sign_up else R.string.error_logging_in
                emitUiState(false, errorMessage = errorResId)
            }
        }
    }

    private suspend fun emitUiState(showProgress: Boolean, errorMessage: Int? = null, user: User? = null) {
        dispatchOnMainThread {
            _uiState.value = AuthUiModel(showProgress, errorMessage, user)
        }
    }


}