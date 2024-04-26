package com.sopt.now.main

import androidx.lifecycle.ViewModel
import com.sopt.now.R
import com.sopt.now.main.adapter.CommonItem
import com.sopt.now.main.adapter.CommonViewType
import com.sopt.now.main.adapter.ViewObject
import com.sopt.now.models.User

class MainViewModel: ViewModel() {

    var userData: User = User()
        private set

    fun setUserData(user: User) {
        userData = user

        mockFriendList.add(0,
            CommonItem(
                viewType = CommonViewType.USER_VIEW.name,
                viewObject = ViewObject.UserViewObject(
                name = userData.nickName,
                description = userData.mbti,
                image = R.drawable.ic_launcher_foreground))
        )
    }



    val mockFriendList = mutableListOf(
        CommonItem(
            viewType = CommonViewType.FRIEND_VIEW.name,
            viewObject = ViewObject.FriendViewObject(
                profileImage = R.drawable.ic_launcher_foreground,
                name = "이의경",
                selfDescription = "다들 빨리 끝내고 뒤풀이 가고 싶지? ㅎㅎ 아직 반도 안왔어 ^&^"
            )
        ),
        CommonItem(
            viewType = CommonViewType.FRIEND_VIEW.name,
            viewObject = ViewObject.FriendViewObject(
                profileImage = R.drawable.ic_launcher_foreground,
                name = "우상욱",
                selfDescription = "나보다 안드 잘하는 사람 있으면 나와봐"
            )
        ),
        CommonItem(
            viewType = CommonViewType.FRIEND_VIEW.name,
            viewObject = ViewObject.FriendViewObject(
                profileImage = R.drawable.ic_launcher_foreground,
                name = "배지현",
                selfDescription = "표정 풀자 ^^"
            )
        ), CommonItem(
            viewType = CommonViewType.FRIEND_VIEW.name,
            viewObject = ViewObject.FriendViewObject(
                profileImage = R.drawable.ic_launcher_foreground,
                name = "최준서",
                selfDescription = "애들아 힘내보자고~!!"
            )
        ), CommonItem(
            viewType = CommonViewType.FRIEND_VIEW.name,
            viewObject = ViewObject.FriendViewObject(
                profileImage = R.drawable.ic_launcher_foreground,
                name = "이유빈",
                selfDescription = "나보다 귀여운 사람 나와봐"
            )
        ), CommonItem(
            viewType = CommonViewType.FRIEND_VIEW.name,
            viewObject = ViewObject.FriendViewObject(
                profileImage = R.drawable.ic_launcher_foreground,
                name = "김언지",
                selfDescription = "오비의 맛을 보여줘야겠고만!"
            )
        ), CommonItem(
            viewType = CommonViewType.FRIEND_VIEW.name,
            viewObject = ViewObject.FriendViewObject(
                profileImage = R.drawable.ic_launcher_foreground,
                name = "박동민",
                selfDescription = "컴포즈는 나에게 맡기라고!"
            )
        ), CommonItem(
            viewType = CommonViewType.FRIEND_VIEW.name,
            viewObject = ViewObject.FriendViewObject(
                profileImage = R.drawable.ic_launcher_foreground,
                name = "배찬우",
                selfDescription = "마지막 20대를 불태워 보겠어.."
            )
        )
    )


}