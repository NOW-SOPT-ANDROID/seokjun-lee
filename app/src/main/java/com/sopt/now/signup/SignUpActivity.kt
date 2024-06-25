package com.sopt.now.signup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.network.dto.RequestSignUpDto

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>(
        factoryProducer = { SignUpViewModel.Factory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserver()
    }

    private fun initView() {
        binding.signupBtnSignup.setOnClickListener {
            viewModel.postSignUp(getSignUpRequestDto())
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) {
            Toast.makeText(
                this@SignUpActivity, it.message, Toast.LENGTH_SHORT,
            ).show()

            if (viewModel.liveData.value?.isSuccess!!) {
                finish()
            }
        }
    }

    private fun getSignUpRequestDto(): RequestSignUpDto = with(binding) {
        return@with RequestSignUpDto(
            authenticationId = signupEtId.text.toString(),
            password = signupEtPw.text.toString(),
            nickname = signupEtNickname.text.toString(),
            phone = signupEtPhone.text.toString()
        )
    }

}