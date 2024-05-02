package com.sopt.now.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.R
import com.sopt.now.main.adapter.CommonItem
import com.sopt.now.main.adapter.CommonViewType
import com.sopt.now.main.adapter.ViewObject
import com.sopt.now.models.User
import com.sopt.now.network.AuthService
import com.sopt.now.network.ServicePool
import com.sopt.now.network.dto.ResponseFollowListDto
import com.sopt.now.network.dto.ResponseMemberInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 팔로워 API와 유저프로필 API의 호출 순서 조정
 */

data class MainState(
    val isSuccess: Boolean,
    var isFollowSuccess: Boolean = false,
    val message: String,
    val userData: User? = null
)

data class FollowState(
    val isSuccess: Boolean,
    val message: String,
    val friendList: MutableList<CommonItem> = mutableListOf<CommonItem>()
)

class MainViewModel : ViewModel() {
    private lateinit var authService: AuthService
    val liveData = MutableLiveData<MainState>()
    val followLiveData = MutableLiveData<FollowState>()

    fun updateMainState(memberId: String) {
        ServicePool.initMainService(memberId)
        authService = ServicePool.mainService

        fetchMemberInfo()
    }

    private fun putUserDataInFollow() {
        followLiveData.value?.friendList?.add(
            0,
            CommonItem(
                viewType = CommonViewType.USER_VIEW.name,
                viewObject = ViewObject.UserViewObject(
                    name = liveData.value?.userData?.nickName?:"",
                    description = liveData.value?.userData?.phoneNum?:"",
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
                            id = data?.data?.authenticationId ?: "",
                            nickName = data?.data?.nickname ?: "",
                            phoneNum = data?.data?.phone ?: ""
                        )
                    )
                } else {
                    val error = response.message()
                    liveData.value = MainState(
                        isSuccess = false,
                        message = "회원정보 불러오기 실패 $error"
                    )
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

    fun fetchFollow(page: Int = 2) {
        ServicePool.followService.getFollow(page).enqueue(object : Callback<ResponseFollowListDto> {
            override fun onResponse(
                call: Call<ResponseFollowListDto>,
                response: Response<ResponseFollowListDto>
            ) {
                if (response.isSuccessful) {
                    val data: ResponseFollowListDto? = response.body()
                    if(data?.data != null){
                        val itemList = convertDataListToCommonItems(data.data)
                        followLiveData.value = FollowState(
                            isSuccess = true,
                            message = response.message()?:"",
                            friendList = itemList ?: mutableListOf()
                        )
                        putUserDataInFollow()
                    }
                } else {
                    val error = response.message()
                    liveData.value = MainState(
                        isSuccess = false,
                        message = "회원정보 불러오기 실패 $error"
                    )
                    Log.d("MainViewModel", "failed")
                }
            }

            override fun onFailure(call: Call<ResponseFollowListDto>, t: Throwable) {
                Log.d("MainViewModel", "onFailure")
            }
        })
    }

    private fun convertDataListToCommonItems(dataList: List<ResponseFollowListDto.Data>): MutableList<CommonItem> {
        val itemList = mutableListOf<CommonItem>()
        for (data in dataList) {
            itemList.add(
                CommonItem(
                    viewType = CommonViewType.FRIEND_VIEW.name,
                    viewObject = ViewObject.FriendViewObject(
                        profileImage = R.drawable.ic_launcher_foreground,
                        name = data.firstName + " " + data.lastName,
                        selfDescription = data.email,
                        imageUrl = data.avatar
                    )
                )
            )
        }
        return itemList
    }
}