package com.sopt.now.main

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.R
import com.sopt.now.login.LoginActivity
import com.sopt.now.main.adapter.CommonItem
import com.sopt.now.main.adapter.CommonViewType
import com.sopt.now.main.adapter.ViewObject
import com.sopt.now.models.User
import com.sopt.now.network.AuthService
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.ResponseMemberInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class MainState(
    val isSuccess: Boolean,
    val message: String,
    val userData: User? = null,
    val friendList: MutableList<CommonItem> = mutableListOf(
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
)

class MainViewModel: ViewModel() {
    private lateinit var authService: AuthService
    val liveData = MutableLiveData<MainState>()

    fun updateMainState(memberId: String) {
        ServicePool.initMainService(memberId)
        authService = ServicePool.mainService
        fetchMemberInfo()
    }

    fun setUserData(user: User) {
        liveData.value?.friendList?.add(
            0,
            CommonItem(
                viewType = CommonViewType.USER_VIEW.name,
                viewObject = ViewObject.UserViewObject(
                    name = user.nickName,
                    description = user.phoneNum,
                    image = R.drawable.ic_launcher_foreground
                )
            )
        )
    }

    private fun fetchMemberInfo() {
        authService.getMemberInfo().enqueue(object : Callback<ResponseMemberInfoDto> {
            override fun onResponse(
                call: Call<ResponseMemberInfoDto>,
                response: Response<ResponseMemberInfoDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseMemberInfoDto? = response.body()
                    liveData.value = MainState(
                        isSuccess = true,
                        message = "회원정보 불러오기 성공",
                        userData = User(
                            id = data?.data?.authenticationId?:"",
                            nickName = data?.data?.nickname?:"",
                            phoneNum = data?.data?.phone?:"")
                    )
                    setUserData(liveData.value?.userData?:User())
                } else {
                    val error = response.message()
                    liveData.value = MainState(
                        isSuccess = false,
                        message = "회원정보 불러오기 실패 $error")
                }
            }

            override fun onFailure(call: Call<ResponseMemberInfoDto>, t: Throwable) {
                liveData.value = MainState(
                    isSuccess = false,
                    message = "서버에러"
                )
                Log.d("MainViewModel", liveData.value!!.message)
            }
        })
    }

}