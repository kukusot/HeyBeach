package com.heybeach.profile.ui

import com.heybeach.profile.domain.model.User

data class UserUiModel(val loading: Boolean, val loggedIn: Boolean, val user: User?, val errorMessage: Int?)