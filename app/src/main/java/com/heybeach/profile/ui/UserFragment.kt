package com.heybeach.profile.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.heybeach.R
import com.heybeach.profile.auth.ui.AuthActivity
import com.heybeach.profile.di.UserFragmentInjector
import com.heybeach.profile.domain.model.LOG_IN
import com.heybeach.profile.domain.model.SIGN_UP
import com.heybeach.profile.domain.model.User
import com.heybeach.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_user.*

const val AUTH_REQUEST_CODE = 1001
const val USER_KEY = "user"
const val ACTION_KEY = "action_key"

class UserFragment : Fragment() {

    lateinit var viewModel: UserViewModel

    override fun onAttach(context: Context?) {
        UserFragmentInjector.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_user, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            startAuthActivity(LOG_IN)
        }

        signupButton.setOnClickListener {
            startAuthActivity(SIGN_UP)
        }

        logOutButton.setOnClickListener {
            viewModel.logOut()
        }

        observeVIewModel()
    }

    private fun startAuthActivity(action: String) {
        val intent = Intent(context, AuthActivity::class.java).apply {
            putExtra(ACTION_KEY, action)
        }
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }

    private fun observeVIewModel() {
        viewModel.userData.observe(this, Observer { uiModel ->
            if (uiModel.loggedIn) {
                showLoggedInState()
            } else {
                showDefaultState()
            }

            progress.setVisibility(uiModel.loading)

            showUserInfo(uiModel.user)

            if (uiModel.errorMessage != null) {
                Snackbar.make(parentView, uiModel.errorMessage, Snackbar.LENGTH_LONG).apply {
                    setAction(R.string.try_again) {
                        viewModel.fetchUser()
                    }
                    show()
                }
            }
        })
    }

    private fun showLoggedInState() {
        loginButton.visibility = INVISIBLE
        signupButton.visibility = INVISIBLE
        logOutButton.visibility = VISIBLE
    }

    private fun showDefaultState() {
        loginButton.visibility = VISIBLE
        signupButton.visibility = VISIBLE
        logOutButton.visibility = INVISIBLE
    }

    private fun showUserInfo(user: User?) {
        val textVisibility = (user != null)
        emailText.apply {
            setVisibility(textVisibility)
            text = getString(R.string.email_formatted, user?.email)
        }
        idText.apply {
            setVisibility(textVisibility)
            text = getString(R.string.id_formatted, user?.id)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val user: User? = data?.getParcelableExtra(USER_KEY)
            viewModel.onLoggedIn(user)
        }
    }

}