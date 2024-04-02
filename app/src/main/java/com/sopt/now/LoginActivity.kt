package com.sopt.now

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.models.User

const val SIGNUP_KEY = "user"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var users: MutableList<User> = mutableListOf()

    private val getSignUpResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra(SIGNUP_KEY, User::class.java)
            } else {
                result.data?.getSerializableExtra(SIGNUP_KEY)
            }
            userData?.let { users.add(it as User) }
            Toast.makeText(this, "회원가입이 완료되었습니다.}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            getSignUpResult.launch(intent)
        }
    }
}