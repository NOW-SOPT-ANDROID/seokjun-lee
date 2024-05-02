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
    val userData: User? = null,
    val friendList: MutableList<CommonItem> = mutableListOf()
)

class MainViewModel : ViewModel() {
    private lateinit var authService: AuthService
    val liveData = MutableLiveData<MainState>()

    fun updateMainState(memberId: String) {
        ServicePool.initMainService(memberId)
        authService = ServicePool.mainService

        fetchMemberInfo()
        fetchFollow()
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
                            id = data?.data?.authenticationId ?: "",
                            nickName = data?.data?.nickname ?: "",
                            phoneNum = data?.data?.phone ?: ""
                        )

                    )
                    setUserData(liveData.value?.userData ?: User())
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

    private fun fetchFollow(page: Int = 2) {
        ServicePool.followService.getFollow(page).enqueue(object : Callback<ResponseFollowListDto> {
            override fun onResponse(
                call: Call<ResponseFollowListDto>,
                response: Response<ResponseFollowListDto>
            ) {
                Log.d("MainViewModel", "onResponse")
                if (response.isSuccessful) {
                    val data: ResponseFollowListDto? = response.body()
                    if(data?.data != null){
                        val itemList = convertDataListToCommonItems(data.data)
                        liveData.value = MainState(
                            isSuccess = liveData.value?.isSuccess!!,
                            isFollowSuccess = true,
                            message = liveData.value?.message!!,
                            userData = liveData.value?.userData,
                            friendList = liveData.value?.friendList!!
                        )
                        liveData.value?.friendList?.addAll(itemList)
                    }
                    Log.d("MainViewModel", liveData.value?.friendList.toString())
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

    private fun convertDataListToCommonItems(dataList: List<ResponseFollowListDto.Data>): List<CommonItem> {
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