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

    /*private fun sendUserDataToLogin(userData: User) {
        with(Intent()) {
            putExtra(SIGNUP_KEY, userData)
            setResult(Activity.RESULT_OK, this)
        }
        finish()
    }

    private fun isSignUpPossible(id: String, pw: String, nickname: String, mbti: String): Boolean {
        var isPossible = false
        val toastMessage = when {
            id.length !in ID_MIN_LEN..ID_MAX_LEN -> R.string.toast_signup_check_id
            pw.length !in PW_MIN_LEN..PW_MAX_LEN -> R.string.toast_signup_check_pw
            nickname.isBlank() -> R.string.toast_signup_check_nickname
            mbti.isBlank() -> R.string.toast_signup_check_mbti
            else -> {
                isPossible = true
                R.string.toast_signup_success
            }
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        return isPossible
    }*/

    private fun getSignUpRequestDto(): RequestSignUpDto {
        val id = binding.signupEtId.text.toString()
        val password = binding.signupEtPw.text.toString()
        val nickname = binding.signupEtNickname.text.toString()
        val phoneNumber = binding.signupEtPhone.text.toString()
        return RequestSignUpDto(
            authenticationId = id,
            password = password,
            nickname = nickname,
            phone = phoneNumber
        )
    }

    companion object {
        const val ID_MAX_LEN = 10
        const val ID_MIN_LEN = 6

        const val PW_MAX_LEN = 12
        const val PW_MIN_LEN = 8
    }
}