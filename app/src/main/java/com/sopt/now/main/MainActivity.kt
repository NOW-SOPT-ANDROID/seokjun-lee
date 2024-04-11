package com.sopt.now.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.now.R
import com.sopt.now.login.LoginActivity.Companion.LOGIN_KEY
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.ext.serializable
import com.sopt.now.login.LoginActivity.Companion.BACK_PRESSED_RESULT_CODE
import com.sopt.now.login.LoginActivity.Companion.LOGOUT_RESULT_CODE
import com.sopt.now.models.User

class MainActivity :AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addOnBackPressedCallback()

        val user = getUserInfoFromIntent()

        val currentFragment = supportFragmentManager.findFragmentById(binding.mainFcvHome.id)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.mainFcvHome.id, HomeFragment(user))
                .commit()
        }
        clickBottomNavigation(user = user)
    }

    private fun clickBottomNavigation(user: User) {
        binding.mainBnvHome.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_home-> {
                    replaceFragment(HomeFragment(user))
                    true
                }
                R.id.menu_search-> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.menu_mypage-> {
                    replaceFragment(MyPageFragment(user, OnClickLogoutButton()))
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
            .replace(R.id.main_fcv_home, fragment)
            .commit()
    }

    private fun getUserInfoFromIntent(): User {
        return intent.serializable<User>(LOGIN_KEY) ?: User()
    }

    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setResult(BACK_PRESSED_RESULT_CODE)
                finish()
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    inner class OnClickLogoutButton: View.OnClickListener{
        override fun onClick(v: View?) {
            setResult(LOGOUT_RESULT_CODE)
            finish()
        }

    }
}