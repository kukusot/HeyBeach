package com.heybeach.profile.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.heybeach.R
import com.heybeach.profile.auth.di.AuthActivityInjector
import com.heybeach.profile.domain.model.*
import com.heybeach.profile.ui.ACTION_KEY
import com.heybeach.profile.ui.USER_KEY
import com.heybeach.utils.closeSoftKeyboard
import com.heybeach.utils.createHtmlText
import com.heybeach.utils.setVisibility
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AuthActivityInjector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val action = intent.getStringExtra(ACTION_KEY)
        setupTitles(action)
        viewModel.action = action

        actionButton.setOnClickListener {
            submitForm()
        }

        passwordEdit.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                v.closeSoftKeyboard()
                submitForm()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        observeViewModel()
    }

    private fun submitForm() {
        val signupModel = SignupModel(emailEdit.text.toString(), passwordEdit.text.toString())
        viewModel.validateAndAuth(signupModel)
    }

    private fun observeViewModel() {
        viewModel.formData.observe(this, Observer { errors ->
            emailLayout.isErrorEnabled = false
            passwordLayout.isErrorEnabled = false

            errors.forEach { error ->
                when (error) {
                    ValidationError.EMAIL -> emailLayout.error = getString(R.string.email_not_valid)
                    ValidationError.PASSWORD -> passwordLayout.error = getString(R.string.password_not_valid)
                }
            }

        })

        viewModel.uiState.observe(this, Observer { uiModel ->
            showProgress(uiModel.showProgress)

            if (uiModel.errorMessage != null) {
                Snackbar.make(parentView, uiModel.errorMessage, Snackbar.LENGTH_LONG).show()
            }

            if (uiModel.authSuccess != null) {
                val user = uiModel.authSuccess
                val welcomeMessage = getString(R.string.welcome, user.email)
                Snackbar.make(parentView, welcomeMessage, Snackbar.LENGTH_LONG).show()
                setResultAndFinish(user)
            }
        })
    }

    private fun setResultAndFinish(user: User) {
        GlobalScope.launch {
            delay(1500)
            val data = Intent().apply {
                putExtra(USER_KEY, user)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun showProgress(show: Boolean) {
        progress.setVisibility(show)
    }

    private fun setupTitles(action: String) {
        val title = getActionName(action)
        supportActionBar?.title = "<font color='#FFFFFF'>$title</font>".createHtmlText()
        actionButton.text = title
    }

    private fun getActionName(action: String): String {
        return if (action == LOG_IN) {
            getString(R.string.login)
        } else {
            getString(R.string.signup)
        }

    }

}