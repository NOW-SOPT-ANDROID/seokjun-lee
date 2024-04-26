package com.sopt.now.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.ext.parcelable
import com.sopt.now.feat.PreferenceManager
import com.sopt.now.main.MainActivity
import com.sopt.now.models.User
import com.sopt.now.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    companion object{
        const val SIGNUP_KEY = "user"
        const val LOGIN_KEY = "login"

        const val LOGOUT_RESULT_CODE = 100
        const val BACK_PRESSED_RESULT_CODE = 101
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferenceManager: PreferenceManager

    private var users: MutableList<User> = mutableListOf()
    private val getIntentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when(result.resultCode) {
            Activity.RESULT_OK -> {
                val userData = result.data?.parcelable<User>(SIGNUP_KEY)
                if(userData != null) {
                    preferenceManager.setProfile(userData)
                    users.add(userData)
                }
            }
            BACK_PRESSED_RESULT_CODE -> {
                this@LoginActivity.finish()
            }
            LOGOUT_RESULT_CODE -> {/*simply returns to LoginActivity*/}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()

        setSharedPreference()
    }

    private fun setSharedPreference() {
        preferenceManager = PreferenceManager(this)
        val user = preferenceManager.getProfile()
        if(user != null) {
            users.add(user)
        }
    }

    private fun initButtons() {
        with(binding) {
            loginBtnLogin.setOnClickListener {
                val id = loginEtId.text.toString()
                val pw = loginEtPw.text.toString()
                val user = findUserByIdAndPw(id, pw)
                if (user != null) {
                    initEditTexts()
                    moveToMainActivity(user)
                }
            }
            loginBtnSignup.setOnClickListener {
                moveToSignUpActivity()
            }
        }
    }


    private fun initEditTexts() {
        with(binding.loginEtId) {
            setText("")
            clearFocus()
        }
        with(binding.loginEtPw) {
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



    private fun moveToMainActivity(user: User) {
        with(Intent(this@LoginActivity, MainActivity::class.java)) {
            putExtra(LOGIN_KEY, user)
            getIntentResult.launch(this)
        }
    }

    private fun moveToSignUpActivity() {
        with(Intent(this@LoginActivity, SignUpActivity::class.java)) {
            getIntentResult.launch(this)
        }
    }
}