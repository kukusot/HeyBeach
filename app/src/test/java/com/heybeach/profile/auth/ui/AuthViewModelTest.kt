package com.heybeach.profile.auth.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.heybeach.app.GlobalProvider
import com.heybeach.http.Response
import com.heybeach.profile.domain.data.AuthProvider
import com.heybeach.profile.domain.model.SignupModel
import com.heybeach.profile.domain.model.User
import com.heybeach.profile.domain.model.UserResponse
import com.heybeach.profile.domain.model.ValidationError
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val authProvider: AuthProvider = mock()
    private lateinit var viewModel: AuthViewModel
    private val action = "ACTION"

    @Before
    fun setup() {
        GlobalProvider.testInit(mock(), mock())
        viewModel = AuthViewModel(authProvider)
        viewModel.action = action
    }

    @Test
    fun `errors_in_form validateAndAuth errors_posted`() {
        val invalidEmail = "email"
        val invalidPassword = "pass"

        viewModel.validateAndAuth(SignupModel(invalidEmail, invalidPassword))

        val postedErrors = viewModel.formData
        assertTrue(postedErrors.value!!.contains(ValidationError.PASSWORD))
        assertTrue(postedErrors.value!!.contains(ValidationError.EMAIL))
    }

    @Test
    fun `valid_email_valid_password validateAndAuth auth_executed`() = runBlocking {
        val validEmail = "email@email.com"
        val validPassword = "123456"
        val id = "id"
        val token = " token"
        val user = User(id, validEmail)
        val userResponse = UserResponse(user, token)
        val response = Response.Success(userResponse)
        whenever(authProvider.doAuth(validEmail, validPassword, action)).thenReturn(response)


        viewModel.validateAndAuth(SignupModel(validEmail, validPassword))

        assertTrue(viewModel.formData.value!!.isEmpty())
        assertFalse(viewModel.uiState.value!!.showProgress)
        assertNull(viewModel.uiState.value!!.errorMessage)
        assertEquals(user, viewModel.uiState.value!!.authSuccess)
    }
}

