package com.sopt.now.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.R
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.feat.PreferenceManager
import com.sopt.now.main.MainActivity
import com.sopt.now.network.dto.RequestLoginDto
import com.sopt.now.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>(
        factoryProducer = {LoginViewModel.Factory}
    )

    private val getIntentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when(result.resultCode) {
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
    }

    private fun initView() {
        with(binding) {
            loginBtnLogin.setOnClickListener {
                viewModel.postLogin(getRequestLoginDto())
            }
            loginBtnSignup.setOnClickListener {
                navigateToSignUpActivity()
            }
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) {loginState->
            if(loginState.isSuccess) {
                showToastMessage(getString(R.string.retrofit_success_login, loginState.message))
                navigateToMainActivity(loginState.message)
            } else {
                showToastMessage(getString(R.string.retrofit_failure_login, loginState.message))
            }
        }
    }

    private fun getRequestLoginDto(): RequestLoginDto = with(binding){
        return@with RequestLoginDto(
            authenticationId = loginEtId.text.toString(),
            password = loginEtPw.text.toString()
        )
    }

    private fun navigateToSignUpActivity() {
        with(Intent(this@LoginActivity, SignUpActivity::class.java)) {
            getIntentResult.launch(this)
        }
    }

    private fun navigateToMainActivity(memberId: String) {
        val sharedPreferences = this.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE)
        sharedPreferences.edit().putString(MEMBER_ID_KEY, memberId).apply()

        with(Intent(this@LoginActivity, MainActivity::class.java)) {
            putExtra(LOGIN_KEY, memberId)
            getIntentResult.launch(this)
        }
    }

    private fun showToastMessage(message: String) =
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

    companion object{
        const val LOGIN_KEY = "login"
        const val MEMBER_ID_KEY = "memberId"
        const val PREFERENCE_KEY = "SOPT"
        const val LOGOUT_RESULT_CODE = 100
        const val BACK_PRESSED_RESULT_CODE = 101
    }
}