package com.sopt.now.password

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.databinding.ActivityPasswordBinding
import com.sopt.now.main.MainViewModel
import com.sopt.now.network.dto.RequestChangePasswordDto

class PasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordBinding
    private val viewModel by viewModels<PasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserve()
        initView()
    }

    private fun initView() {
        with(binding) {
            passwordBtnProceed.setOnClickListener {
                val request = getRequestChangePasswordDto()
                viewModel.patchPassword(request)
            }
            passwordBtnCancel.setOnClickListener {
                finish()
            }
        }
    }

    private fun initObserve() {
        viewModel.liveData.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            Log.d("PasswordActivity", it.message)
            if(it.isSuccess) {
                //finish()
            }
        }
    }

    private fun getRequestChangePasswordDto(): RequestChangePasswordDto = with(binding) {
        return@with RequestChangePasswordDto(
            previousPassword = passwordEtBefore.text.toString(),
            newPassword = passwordEtNew.text.toString(),
            newPasswordVerification = passwordEtNewCheck.text.toString(),
        )
    }
}
