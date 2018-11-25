package com.heybeach.profile.domain.data

import com.heybeach.profile.domain.model.SignupModel
import com.heybeach.profile.domain.model.ValidationError
import com.heybeach.profile.domain.model.ValidationError.*
import java.util.regex.Pattern

val EMAIL_ADDRESS = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)!!

fun validateForm(model: SignupModel) = arrayListOf<ValidationError>().apply {
    if (!EMAIL_ADDRESS.matcher(model.email).matches()) {
        add(EMAIL)
    }

    if (model.password.length < 6) {
        add(PASSWORD)
    }
}
