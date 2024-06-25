package com.sopt.now.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sopt.now.R
import com.sopt.now.SoptApplication
import com.sopt.now.container.repository.AuthRepository
import com.sopt.now.container.repository.FollowerRepository
import com.sopt.now.login.LoginViewModel
import com.sopt.now.main.adapter.CommonItem
import com.sopt.now.main.adapter.CommonViewType
import com.sopt.now.main.adapter.ViewObject
import com.sopt.now.models.User
import com.sopt.now.network.dto.ResponseMemberInfoDto
import com.sopt.now.network.dto.convertDataListToCommonItems
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

/**
 * 팔로워 API와 유저프로필 API의 호출 순서 조정
 */

class MainViewModel(
    private val authRepository: AuthRepository,
    private val followerRepository: FollowerRepository
) : ViewModel() {
    val liveData = MutableLiveData<MainState>()
    val followLiveData = MutableLiveData<FollowState>()

    private fun putUserDataInFollow() {
        followLiveData.value?.friendList?.add(
            0,
            CommonItem(
                viewType = CommonViewType.USER_VIEW.name,
                viewObject = ViewObject.UserViewObject(
                    name = liveData.value?.userData?.nickName ?: "",
                    description = liveData.value?.userData?.phoneNum ?: "",
                    image = R.drawable.ic_launcher_foreground
                )
            )
        )
    }

    fun updateMainState() {
        getMemberInfo()
    }

    private fun getMemberInfo() = viewModelScope.launch {
        runCatching {
            authRepository.getMemberInfo()
        }.onSuccess { response ->
            if (response.isSuccessful) {
                val data: ResponseMemberInfoDto? = response.body()
                liveData.value = MainState(
                    isSuccess = true,
                    message = data?.data?.authenticationId ?: "",
                    userData = User(
                        id = data?.data?.authenticationId ?: "",
                        nickName = data?.data?.nickname ?: "",
                        phoneNum = data?.data?.phone ?: ""
                    )
                )
                getFollowerList()
            } else {
                val error = response.errorBody()?.string()

                if (error != null) {
                    val jsonMessage = Json.parseToJsonElement(error)

                    liveData.value = MainState(
                        isSuccess = false,
                        message = jsonMessage.jsonObject[LoginViewModel.JSON_NAME].toString()
                    )
                }
            }
        }.onFailure {
            liveData.value = MainState(
                isSuccess = false,
                message = SERVER_ERROR_MESSAGE
            )
        }
    }

    private fun getFollowerList() = viewModelScope.launch {
        runCatching {
            followerRepository.getFollowerList(2)
        }.onSuccess { response ->
                val itemList = convertDataListToCommonItems(response.data)
                followLiveData.value = FollowState(
                    isSuccess = true,
                    friendList = itemList
                )
                putUserDataInFollow()

        }.onFailure {
            followLiveData.value = FollowState(
                isSuccess = false,
                message = SERVER_ERROR_MESSAGE
            )
        }
    }

    companion object {
        const val SERVER_ERROR_MESSAGE = "서버에러"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as SoptApplication
                val authRepository = application.appContainer.authAfterLoinRepository
                val followerRepository = application.appContainer.followerRepository
                MainViewModel(authRepository = authRepository, followerRepository = followerRepository)
            }
        }
    }
}