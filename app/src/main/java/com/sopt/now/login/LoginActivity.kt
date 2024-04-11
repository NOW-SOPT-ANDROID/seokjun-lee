package com.sopt.now.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
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
            //Toast.makeText(this, getString(R.string.toast_signup_success), Toast.LENGTH_SHORT).show()
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
                val user = findUserByIdAndPw(id, pw)
                if (user != null) {
                    initEditTexts()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(LOGIN_KEY, user)
                    getIntentResult.launch(intent)
                }
            }

            loginBtnSignup.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                getIntentResult.launch(intent)
            }
        }
    }


    private fun initEditTexts() {
        binding.loginEtId.apply {
            setText("")
            clearFocus()
        }
        binding.loginEtPw.apply {
            setText("")
            clearFocus()
        }
    }

    private fun findUserByIdAndPw(id: String, pw: String): User? {
        var userData: User? = null
        var toastMessage = 0
        when{
            id.isBlank() -> {toastMessage = R.string.toast_login_fail_blank_id}
            pw.isBlank() -> {toastMessage = R.string.toast_login_fail_blank_pw}
            else -> {
                users.forEach {user ->
                    if(user.id == id) {
                        if(user.pw == pw) {
                            userData = user
                            toastMessage = R.string.toast_login_success
                            return@forEach
                        } else {
                            toastMessage = R.string.toast_login_fail_wrong_pw
                        }
                    } else {
                        toastMessage = R.string.toast_login_fail_wrong_id
                    }
                }
            }
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        return userData
    }
}