package com.sopt.now.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.ext.parcelable
import com.sopt.now.feat.PreferenceManager
import com.sopt.now.main.MainActivity
import com.sopt.now.models.User
import com.sopt.now.network.dto.RequestLoginDto
import com.sopt.now.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

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
        initView()
        initObserver()
        setSharedPreference()
    }

    private fun setSharedPreference() {
        preferenceManager = PreferenceManager(this)
        val user = preferenceManager.getProfile()
        if(user != null) {
            users.add(user)
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) {loginState->
            Toast.makeText(
                this@LoginActivity,
                loginState.message,
                Toast.LENGTH_SHORT,
            ).show()
            if(loginState.isSuccess) {
                moveToMainActivity(loginState.memberId?:"")
            }
        }
    }

    private fun initView() {
        with(binding) {
            loginEtId.setText("seokjun")
            loginEtPw.setText("seokjun2000!")

            loginBtnLogin.setOnClickListener {
                viewModel.login(getRequestLoginDto())
            }
            loginBtnSignup.setOnClickListener {
                moveToSignUpActivity()
            }
        }
    }

    fun getRequestLoginDto(): RequestLoginDto = RequestLoginDto(
        authenticationId = binding.loginEtId.text.toString(),
        password = binding.loginEtPw.text.toString()
    )

    private fun moveToMainActivity(memberId: String) {
        with(Intent(this@LoginActivity, MainActivity::class.java)) {
            putExtra(LOGIN_KEY, memberId)
            getIntentResult.launch(this)
        }
    }

    private fun moveToSignUpActivity() {
        with(Intent(this@LoginActivity, SignUpActivity::class.java)) {
            getIntentResult.launch(this)
        }
    }

    companion object{
        const val SIGNUP_KEY = "user"
        const val LOGIN_KEY = "login"

        const val LOGOUT_RESULT_CODE = 100
        const val BACK_PRESSED_RESULT_CODE = 101
    }
}