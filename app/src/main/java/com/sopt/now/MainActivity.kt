package com.sopt.now

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.models.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = getUserInfoFromIntent()

        binding.apply {
            tvNickname.text = user.nickName
            tvIntro.text = "제 MBTI는 ${user.mbti} 입니다!!"
            tvId.text = user.id
            tvPw.text = user.pw
        }
    }

    private fun getUserInfoFromIntent(): User {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(LOGIN_KEY, User::class.java)
        } else {
            intent.getSerializableExtra(LOGIN_KEY)
        }
        return if (data != null) {
            data as User
        } else User()
    }
}