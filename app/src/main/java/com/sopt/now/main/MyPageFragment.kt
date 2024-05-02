package com.sopt.now.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sopt.now.R
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.login.LoginActivity
import com.sopt.now.models.User
import com.sopt.now.password.PasswordActivity

class MyPageFragment: Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = requireNotNull(_binding) {"초기화 좀 시켜보시오"}

    private val sharedViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        //전체 뷰를 반환하는 구문
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextViews(sharedViewModel.liveData.value?.userData!!)
        initButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTextViews(user: User) {
        with(binding){
            myPageTvNickname.text = user.nickName
            myPageTvIntro.text = getString(R.string.mypage_tv_phone_num, user.phoneNum)
            myPageTvId.text = user.id
        }
    }

    private fun initButton() {
        with(activity as MainActivity){
            binding.myPageBtnChangePw.setOnClickListener {
                val intent = Intent(this, PasswordActivity::class.java)
                startActivity(intent)
            }

            binding.myPageBtnLogout.setOnClickListener {
                this.setResult(LoginActivity.LOGOUT_RESULT_CODE)
                this.finish()
            }
        }
    }

}