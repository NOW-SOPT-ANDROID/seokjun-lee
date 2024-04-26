package com.sopt.now.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.login.LoginActivity.Companion.SIGNUP_KEY
import com.sopt.now.models.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        with(binding) {
            signupBtnSignup.setOnClickListener {
                val id = signupEtId.text.toString()
                val pw = signupEtPw.text.toString()
                val nickname = signupEtNickname.text.toString()
                val mbti = signupEtMbti.text.toString()

                if (isSignUpPossible(id, pw, nickname, mbti)) {
                    sendUserDataToLogin(
                        User(
                            id = id,
                            pw = pw,
                            nickName = nickname,
                            mbti = mbti)
                    )
                }
            }
        }
    }

    private fun sendUserDataToLogin(userData: User) {
        with(Intent()) {
            putExtra(SIGNUP_KEY, userData)
            setResult(Activity.RESULT_OK, this)
        }
        finish()
    }

    private fun isSignUpPossible(id: String, pw: String, nickname: String, mbti: String): Boolean{
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
    }

    companion object{
        const val ID_MAX_LEN = 10
        const val ID_MIN_LEN = 6

        const val PW_MAX_LEN = 12
        const val PW_MIN_LEN = 8
    }
}