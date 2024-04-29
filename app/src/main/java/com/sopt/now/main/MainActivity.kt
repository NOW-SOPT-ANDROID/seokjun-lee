package com.sopt.now.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.ext.parcelable
import com.sopt.now.login.LoginActivity.Companion.BACK_PRESSED_RESULT_CODE
import com.sopt.now.login.LoginActivity.Companion.LOGIN_KEY
import com.sopt.now.login.LoginActivity.Companion.LOGOUT_RESULT_CODE
import com.sopt.now.models.User

class MainActivity :AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateMainState()
        initObserve()
    }

    private fun updateMainState() {
        val memberId = intent.getStringExtra(LOGIN_KEY)
        if(memberId != null) {
            viewModel.updateMainState(memberId)
        }
    }

    private fun initObserve() {
        viewModel.liveData.observe(this) {
            if(it.isSuccess) {
                setFragmentManager(HomeFragment())
                setOnClickBottomNavigation()
                setOnBackPressedCallback()
            }
        }
    }

    private fun setOnClickBottomNavigation() {
        binding.mainBnvHome.setOnItemSelectedListener{
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
                    replaceFragment(MyPageFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragmentManager(startFragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(binding.mainFcvHome.id)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.mainFcvHome.id, startFragment)
                .commit()
        }
    }

    // Activity에서 Fragment를 다뤄야 하니 supportFragmentManager를 사용합니다.
    // 트렌젝션을 시작하고 replace 메서드를 통해 Fragment를 교체합니다.
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fcv_home, fragment)
            .commit()
    }

    private fun setOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setResult(BACK_PRESSED_RESULT_CODE)
                finish()
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }
}