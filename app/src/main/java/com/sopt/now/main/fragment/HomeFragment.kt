package com.sopt.now.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.main.MainViewModel
import com.sopt.now.main.adapter.CommonListAdapter

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var _adapter: CommonListAdapter? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 초기화 요망" }

    private val commonListAdapter: CommonListAdapter
        get() = requireNotNull(_adapter) { "어뎁터 초기화 요망"}

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
        initObserve()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserve() {
        sharedViewModel.followLiveData.observe(viewLifecycleOwner) {
            if(it.isSuccess) {
                setFriendAdapter()
            }
        }
    }

    private fun setFriendAdapter() {
        _adapter = CommonListAdapter(sharedViewModel.followLiveData.value?.friendList ?: mutableListOf())
        binding.homeRvFriends.run {
            adapter = commonListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }
}