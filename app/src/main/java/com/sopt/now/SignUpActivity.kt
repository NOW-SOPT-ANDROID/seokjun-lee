package com.sopt.now

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.models.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSignup.setOnClickListener {
                val id = etId.text.toString()
                val pw = etPw.text.toString()
                val nickname = signEtNickname.text.toString()
                val mbti = etMbti.text.toString()
                if (isSignUpPossible(id, pw, nickname, mbti)) {
                    val userData = User(id = id, pw = pw, nickName = nickname, mbti = mbti)
                    val intent = Intent()
                    intent.putExtra(SIGNUP_KEY, userData)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    private fun isSignUpPossible(id: String, pw: String, nickname: String, mbti: String): Boolean{
        var isPossible = false
        val toast = when {
            id.length !in 6..10 -> "ID는 6~10 글자"
            pw.length !in 8..12 -> "비밀번호는 8~12 글자"
            nickname.isBlank() -> "닉네임은 한 글자 이상, 공백 불가"
            mbti.isBlank() -> "MBTI를 입력해주세요"
            else -> {
                isPossible = true
                "회원가입에 성공하였습니다."
            }
        }

        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
        return isPossible
    }
}