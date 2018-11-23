package com.heybeach.profile.auth.ui

import com.heybeach.profile.domain.model.User

data class AuthUiModel(val showProgress: Boolean, val errorMessage: Int?, val authSuccess: User?)