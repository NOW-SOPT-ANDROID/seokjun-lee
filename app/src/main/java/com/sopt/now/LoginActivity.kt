package com.sopt.now

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.ext.serializable
import com.sopt.now.models.User
import java.io.Serializable

class LoginActivity : AppCompatActivity() {
    companion object{
        const val SIGNUP_KEY = "user"
        const val LOGIN_KEY = "login"
    }

    private lateinit var binding: ActivityLoginBinding
    private var users: MutableList<User> = mutableListOf()
    private val getSignUpResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val userData = result.data?.serializable<User>(SIGNUP_KEY)
            if(userData != null) {
                users.add(userData)
                Toast.makeText(this, "회원가입이 완료되었습니다.}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() {
        binding.apply {
            loginBtnLogin.setOnClickListener {
                val id = loginEtId.text.toString()
                val pw = loginEtPw.text.toString()
                val index = checkIdAndPw(id, pw)
                if (index != null) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(LOGIN_KEY, users[index])
                    startActivity(intent)
                }
            }

            loginBtnSignup.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                getSignUpResult.launch(intent)
            }
        }
    }

    private fun checkIdAndPw(id: String, pw: String): Int? {
        var answer: Int? = null
        var toastMessage = ""
        when{
            id.isBlank() -> {toastMessage = "아이디를 입력해주세요"}
            pw.isBlank() -> {toastMessage = "비밀번호를 입력해주세요"}
            else -> {
                for (index in users.indices) {
                    if (users[index].id == id) {
                        if (users[index].pw == pw) {
                            answer = index
                            toastMessage = "로그인에 성공했습니다."
                            break
                        } else {
                            toastMessage = "비밀번호를 다시 확인하세요"
                        }
                    } else {
                        toastMessage = "아이디를 다시 확인하세요"
                    }
                }
            }
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        return answer
    }
}