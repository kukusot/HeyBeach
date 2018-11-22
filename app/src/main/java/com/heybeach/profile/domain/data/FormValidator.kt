package com.heybeach.profile.domain.data

import android.util.Patterns
import com.heybeach.profile.domain.model.SignupModel
import com.heybeach.profile.domain.model.ValidationError
import com.heybeach.profile.domain.model.ValidationError.*

fun validateForm(model: SignupModel) = arrayListOf<ValidationError>().apply {
    if (!Patterns.EMAIL_ADDRESS.matcher(model.email).matches()) {
        add(EMAIL)
    }

    if (model.password.length < 6) {
        add(PASSWORD)
    }
}
