package com.sopt.now.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.signup.SignUpActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.ext.serializable
import com.sopt.now.main.MainActivity
import com.sopt.now.models.User

class LoginActivity : AppCompatActivity() {
    companion object{
        const val SIGNUP_KEY = "user"
        const val LOGIN_KEY = "login"

        const val LOGOUT_RESULT_CODE = 100
        const val BACK_PRESSED_RESULT_CODE = 101
    }

    private lateinit var binding: ActivityLoginBinding
    private var users: MutableList<User> = mutableListOf(
        User("test", "1234","관리자", "TEST")
    )
    private val getIntentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when(result.resultCode) {
            Activity.RESULT_OK -> {
                registerUser(result.data?.serializable<User>(SIGNUP_KEY))
            }
            BACK_PRESSED_RESULT_CODE -> {
                this@LoginActivity.finish()
            }
            LOGOUT_RESULT_CODE -> {}
        }
    }

    private fun registerUser(userData: User?) {
        if(userData != null) {
            users.add(userData)
            Toast.makeText(this, "회원가입이 완료되었습니다.}", Toast.LENGTH_SHORT).show()
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
                    initEditText(binding.loginEtId)
                    initEditText(binding.loginEtPw)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(LOGIN_KEY, users[index])
                    getIntentResult.launch(intent)
                }
            }

            loginBtnSignup.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                getIntentResult.launch(intent)
            }
        }
    }


    private fun initEditText(editText: EditText) {
        editText.apply {
            setText("")
            clearFocus()
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