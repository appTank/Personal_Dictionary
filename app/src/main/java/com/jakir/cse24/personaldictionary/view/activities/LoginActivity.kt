package com.jakir.cse24.personaldictionary.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.databinding.ActivityLoginBinding
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.view_model.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    override fun getContentView() {
        if (PreferenceManager.isLoggedIn) {
            startActivity(Intent(this,
                DashboardActivity::class.java ))
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
//        settingActionBar(getString(R.string.login), false)
        viewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]

        binding.loginViewModel = viewModel

        btnLogin.setOnClickListener {
            val email = viewModel.email.value
            val password = viewModel.password.value

            if (email == null || email == "") {
                binding.etEmail.error = getString(R.string.email_hint)
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (!isEmailValid(email)) {
                binding.etEmail.error = getString(R.string.email_validation_error)
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (password == null || password == "") {
                binding.etPassword.error = getString(R.string.password_hint)
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            showProgressDialog("Checking...")
            viewModel.login(email,password).observe(this, Observer<ResponseModel> {
                hideProgressDialog()
                if (it.status) {
                    startActivity(Intent(this@LoginActivity,
                        DashboardActivity::class.java ))
                    PreferenceManager.isLoggedIn = true
                } else {
                    showToast(it.message)
                }
            })
        }

        tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding.etEmail.setText("")
        binding.etPassword.setText("")
    }
}