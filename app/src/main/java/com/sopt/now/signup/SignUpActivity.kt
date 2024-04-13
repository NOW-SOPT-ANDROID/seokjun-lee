package com.sopt.now.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.login.LoginActivity.Companion.SIGNUP_KEY
import com.sopt.now.databinding.ActivitySignUpBinding
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
        binding.apply {
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
        val intent = Intent()
        intent.putExtra(SIGNUP_KEY, userData)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun isSignUpPossible(id: String, pw: String, nickname: String, mbti: String): Boolean{
        var isPossible = false
        val toastMessage = when {
            id.length !in 6..10 -> R.string.toast_signup_check_id
            pw.length !in 8..12 -> R.string.toast_signup_check_pw
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
}