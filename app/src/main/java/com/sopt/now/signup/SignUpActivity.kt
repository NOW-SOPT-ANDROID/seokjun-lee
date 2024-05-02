package com.sopt.now.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.login.LoginActivity.Companion.SIGNUP_KEY
import com.sopt.now.models.User
import com.sopt.now.network.ServicePool.authService
import com.sopt.now.network.dto.RequestSignUpDto
import com.sopt.now.network.dto.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserver()
    }

    private fun initView() {
        binding.signupBtnSignup.setOnClickListener {
            viewModel.signUp(getSignUpRequestDto())
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) {
            Toast.makeText(
                this@SignUpActivity,
                it.message,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    private fun getSignUpRequestDto(): RequestSignUpDto = with(binding) {
            val id = signupEtId.text.toString()
            val password = signupEtPw.text.toString()
            val nickname = signupEtNickname.text.toString()
            val phoneNumber = signupEtPhone.text.toString()

            return@with RequestSignUpDto(
                authenticationId = id,
                password = password,
                nickname = nickname,
                phone = phoneNumber
            )
        }

}