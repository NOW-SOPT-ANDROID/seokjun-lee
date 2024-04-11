package com.sopt.now.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.R
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.week02.friend.Friend
import com.sopt.week02.friend.FriendAdapter

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) {"초기화 좀 시켜보시오"}

    private val mockFriendList = listOf<Friend>(
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "이의경",
            selfDescription = "다들 빨리 끝내고 뒤풀이 가고 싶지? ㅎㅎ 아직 반도 안왔어 ^&^",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "우상욱",
            selfDescription = "나보다 안드 잘하는 사람 있으면 나와봐",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "배지현",
            selfDescription = "표정 풀자 ^^",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "최준서",
            selfDescription = "애들아 힘내보자고~!!",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "이유빈",
            selfDescription = "나보다 귀여운 사람 나와봐",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "김언지",
            selfDescription = "오비의 맛을 보여줘야겠고만!",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "박동민",
            selfDescription = "컴포즈는 나에게 맡기라고!",
        ),
        Friend(
            profileImage = R.drawable.ic_launcher_foreground,
            name = "배찬우",
            selfDescription = "마지막 20대를 불태워 보겠어..",
        )
    )

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

        val friendAdapter = FriendAdapter()
        binding.rvHomeFriends.run {
            adapter = friendAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        friendAdapter.setFriendList(mockFriendList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}