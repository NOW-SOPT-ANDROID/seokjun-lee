package com.sopt.now.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.models.User

class MyPageFragment(private val user: User): Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = requireNotNull(_binding) {"초기화 좀 시켜보시오"}

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
        initTextViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initTextViews() {
        binding.apply {
            myPageTvNickname.text = user.nickName
            myPageTvIntro.text = "제 MBTI는 ${user.mbti} 입니다!!"
            myPageTvId.text = user.id
            myPageTvPw.text = user.pw
        }
    }

}