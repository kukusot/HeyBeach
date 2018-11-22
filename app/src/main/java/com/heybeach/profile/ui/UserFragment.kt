package com.heybeach.profile.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.heybeach.R
import com.heybeach.profile.auth.ui.AuthActivity
import com.heybeach.profile.di.ProfileInjector
import com.heybeach.profile.domain.model.ACTION_KEY
import com.heybeach.profile.domain.model.LOG_IN
import com.heybeach.profile.domain.model.SIGN_UP
import kotlinx.android.synthetic.main.fragment_user.*

const val AUTH_REQUEST_CODE = 1001

class UserFragment : Fragment() {

    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_user, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        loginButton.setOnClickListener {
            startAuthActivity(LOG_IN)
        }

        signupButton.setOnClickListener {
            startAuthActivity(SIGN_UP)
        }
    }

    private fun startAuthActivity(action: String) {
        val intent = Intent(context, AuthActivity::class.java).apply {
            putExtra(ACTION_KEY, action)
        }
        startActivityForResult(intent, AUTH_REQUEST_CODE)

    }


    override fun onAttach(context: Context?) {
        ProfileInjector.inject(this)
        super.onAttach(context)
    }

}