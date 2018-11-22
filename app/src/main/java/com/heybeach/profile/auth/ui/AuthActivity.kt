package com.heybeach.profile.auth.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.heybeach.R
import com.heybeach.profile.auth.di.AuthActivityInjector
import com.heybeach.profile.domain.model.ACTION_KEY
import com.heybeach.profile.domain.model.LOG_IN
import com.heybeach.profile.domain.model.SignupModel
import com.heybeach.profile.domain.model.ValidationError
import com.heybeach.utils.createHtmlText
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AuthActivityInjector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel::class.java)

        val action = intent.getStringExtra(ACTION_KEY)
        setupTitles(action)
        viewModel.action = action

        actionButton.setOnClickListener {
            val signupModel = SignupModel(emailEdit.text.toString(), passwordEdit.text.toString())
            viewModel.signup(signupModel)
        }

        observeViewModel()
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
        viewModel.networkState.observe(this, Observer { })
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