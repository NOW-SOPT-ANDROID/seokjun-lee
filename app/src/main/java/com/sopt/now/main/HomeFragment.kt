package com.sopt.now.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.main.adapter.CommonListAdapter

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "초기화 좀 시켜보시오" }

    private val sharedViewModel by activityViewModels<MainViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //전체 뷰를 반환하는 구문
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFriendAdapter()
    }



    private fun setFriendAdapter() {
        val commonListAdapter = CommonListAdapter(sharedViewModel.mockFriendList)
        binding.homeRvFriends.run {
            adapter = commonListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}