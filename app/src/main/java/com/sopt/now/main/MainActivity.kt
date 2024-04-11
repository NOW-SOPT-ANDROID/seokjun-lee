package com.sopt.now.main

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.now.R
import com.sopt.now.login.LoginActivity.Companion.LOGIN_KEY
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.models.User
/*

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
}*/
class MainActivity :AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = getUserInfoFromIntent()

        val currentFragment = supportFragmentManager.findFragmentById(binding.fcvHome.id)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.fcvHome.id, HomeFragment())
                .commit()
        }

        clickBottomNavigation(user = user)
    }

    private fun clickBottomNavigation(user: User) {
        binding.bnvHome.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_home-> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.menu_search-> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.menu_mypage-> {
                    replaceFragment(MyPageFragment(user))
                    true
                }
                else -> false
            }
        }
    }

    // Activity에서 Fragment를 다뤄야 하니 supportFragmentManager를 사용합니다.
// 트렌젝션을 시작하고 replace 메서드를 통해 Fragment를 교체합니다.
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_home, fragment)
            .commit()
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