package com.heybeach.profile.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heybeach.core.BaseViewModel
import com.heybeach.http.*
import com.heybeach.profile.domain.model.User
import kotlinx.coroutines.launch

class UserViewModel : BaseViewModel() {

    private val _userData = MutableLiveData<Response<User>>()
    val userData: LiveData<Response<User>>
        get() = _userData

    init {
        scope.launch {
            val params = HttpParams(
                "user/me",
                RequestMethod.GET,
                headers = hashMapOf("x-auth" to "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YmY3MTRhY2YwODNlYzAwMTI0OGJkMmUiLCJhY2Nlc3MiOiJhdXRoIiwiaWF0IjoxNTQyOTE5MzQwfQ.VeFHeuxAwo7iPGcrwZs_JzuvOdwrNGq5NJXScod8XsA")
            )
            val response = executeHttpRequest(params) { stream, headers ->
                stream.readResponseAndClose()
            }.await()
            Log.e("fiko", "me response " + response)
        }
    }


}