package com.heybeach.profile.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.heybeach.R
import com.heybeach.profile.auth.ui.AuthActivity
import com.heybeach.profile.di.UserFragmentInjector
import com.heybeach.profile.domain.model.ACTION_KEY
import com.heybeach.profile.domain.model.LOG_IN
import com.heybeach.profile.domain.model.SIGN_UP
import com.heybeach.profile.domain.model.User
import kotlinx.android.synthetic.main.fragment_user.*

const val AUTH_REQUEST_CODE = 1001
const val USER_KEY = "user"

class UserFragment : Fragment() {


    lateinit var viewModel: UserViewModel

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
    }

    private fun startAuthActivity(action: String) {
        val intent = Intent(context, AuthActivity::class.java).apply {
            putExtra(ACTION_KEY, action)
        }
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val user: User? = data?.getParcelableExtra(USER_KEY)
            Toast.makeText(context, "welcome " + user?.email, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onAttach(context: Context?) {
        UserFragmentInjector.inject(this)
        super.onAttach(context)
    }

}