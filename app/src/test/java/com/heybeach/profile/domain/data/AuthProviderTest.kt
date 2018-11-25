package com.heybeach.profile.domain.data

import android.content.SharedPreferences
import com.heybeach.http.Response
import com.heybeach.profile.domain.model.SIGN_UP
import com.heybeach.profile.domain.model.User
import com.heybeach.profile.domain.model.UserResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class AuthProviderTest {

    private lateinit var authProvider: AuthProvider

    private val sharedPreferences: SharedPreferences = mock()
    private val remoteDataSource: UserRemoteDataSource = mock()

    private val token = "token"

    @Before
    fun setup() {
        authProvider = AuthProvider(sharedPreferences, remoteDataSource)
    }

    @Test
    fun `preferences_has_token provider_has_user`() {
        whenever(sharedPreferences.contains(AUTH_KEY)).thenReturn(true)

        assertTrue(authProvider.hasUser())
    }

    @Test
    fun `preferences_dont_have_token provider_dont_have_user`() {
        whenever(sharedPreferences.contains(AUTH_KEY)).thenReturn(false)

        assertFalse(authProvider.hasUser())
    }

    @Test
    fun `preferences_has_token token correct_token`() {
        val token = "token"
        whenever(sharedPreferences.getString(AUTH_KEY, "")).thenReturn(token)

        assertEquals(token, authProvider.token)
    }

    @Test
    fun `setToken token_is_set`() {
        val editor = mockPreferenceEditor()

        authProvider.token = token

        verify(editor).putString(AUTH_KEY, token)
        verify(editor).apply()
    }

    private fun mockPreferenceEditor(): SharedPreferences.Editor {
        val editor: SharedPreferences.Editor = Mockito.mock(SharedPreferences.Editor::class.java)
        whenever(sharedPreferences.edit()).thenReturn(editor)
        return editor
    }

    @Test
    fun `auth_called success correct_response`() = runBlocking {
        val email = "email"
        val password = "password"
        val expected = Response.Success(UserResponse(User(email, password), "token"))
        whenever(remoteDataSource.signup(email, password)).thenReturn(expected)
        mockPreferenceEditor()

        val response = authProvider.doAuth(email, password, SIGN_UP)

        assertEquals(expected, response)
    }

    @Test
    fun `logged_in logout logout_remote_and_locally`() = runBlocking {
        val editor = mockPreferenceEditor()
        whenever(sharedPreferences.getString(AUTH_KEY, "")).thenReturn(token)
        val user = User("id", "email")
        authProvider.mockUser(user)

        authProvider.logOut()

        verify(editor).remove(AUTH_KEY)
        verify(editor).apply()
        verify(remoteDataSource).logOut(token)
        assertNull(authProvider.user)
    }
}