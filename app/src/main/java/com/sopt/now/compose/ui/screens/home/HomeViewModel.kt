package com.sopt.now.compose.ui.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.MainActivity.Companion.NAVIGATE_LOGIN_KEY
import com.sopt.now.compose.R
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.FollowRepository
import com.sopt.now.compose.models.Follow
import com.sopt.now.compose.models.Friend
import com.sopt.now.compose.models.User
import com.sopt.now.compose.network.ServicePool
import com.sopt.now.compose.network.ServicePool.authService
import com.sopt.now.compose.network.dto.ResponseMemberInfoDto
import com.sopt.now.compose.ui.screens.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "HomeViewModel"

data class HomeState(
    var isMemberSuccess: Boolean = false,
    var isFollowSuccess: Boolean = false,
    var user: User ?= null,
    var follow: List<Follow> ?= null
)

class HomeViewModel(
    private val followRepository: FollowRepository
): ViewModel(){
    private val state:MutableState<HomeState> = mutableStateOf(HomeState())

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private fun updateUiState(user: User?, follow: List<Follow>?) {
        if(state.value.isMemberSuccess && state.value.isFollowSuccess) {
            _uiState.value = HomeUiState.Success(user?:User(), follow?: listOf())
            Log.d(TAG, follow.toString())
        } else {
            _uiState.value = HomeUiState.Error
        }
    }

    fun fetchDatas(navController: NavHostController) {
        val memberId = navController.previousBackStackEntry?.savedStateHandle
            ?.getLiveData<String>(NAVIGATE_LOGIN_KEY)?.value

        Log.d(TAG, memberId.toString())
        if(memberId != null){
            fetchMemberInfo(memberId = memberId)
        }
    }

    private fun fetchMemberInfo(memberId: String = "66") {
        ServicePool.initMainService(memberId)
        val authService = ServicePool.mainService
        authService.getMemberInfo().enqueue(object : Callback<ResponseMemberInfoDto> {
            override fun onResponse(
                call: Call<ResponseMemberInfoDto>,
                response: Response<ResponseMemberInfoDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseMemberInfoDto? = response.body()
                    state.value.isMemberSuccess = true
                    state.value.user = User(
                        id = data?.data?.authenticationId ?: "",
                        nickName = data?.data?.nickname ?: "",
                        mbti = data?.data?.phone ?: ""
                    )
                    fetchFollowList()
                } else {
                    val error = response.errorBody()?.string()

                    if(error != null) {
                        val jsonMessage = Json.parseToJsonElement(error)
                        Log.d(TAG, jsonMessage.jsonObject[LoginViewModel.JSON_NAME].toString())
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMemberInfoDto>, t: Throwable) {
                Log.d(TAG, "서버에러")
            }
        })
    }

    private fun fetchFollowList() = viewModelScope.launch {
        val followList = followRepository.fetchFollow()
        if(followList != null) {
            state.value.isFollowSuccess = true
            state.value.follow = followList

            updateUiState(user = state.value.user, follow = state.value.follow)
        }
    }


    fun onBackPressed(navController: NavHostController){
        navController.run {
            previousBackStackEntry?.savedStateHandle?.set(NAVIGATE_BACK_PRESSED_KEY, NAVIGATE_BACK_PRESSED_KEY)
            navigateUp()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SoptApplication)
                val followRepository = application.appContainer.followRepository
                HomeViewModel(followRepository = followRepository)
            }
        }
    }
}