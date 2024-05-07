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
import com.sopt.now.compose.SoptApplication
import com.sopt.now.compose.container.FollowerRepository
import com.sopt.now.compose.container.NetworkMemberRepository
import com.sopt.now.compose.models.Follower
import com.sopt.now.compose.models.User
import com.sopt.now.compose.network.ServicePool
import com.sopt.now.compose.network.dto.ResponseMemberInfoDto
import com.sopt.now.compose.ui.screens.login.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    var follower: List<Follower> ?= null
)

class HomeViewModel(
    private val followerRepository: FollowerRepository,
    private val authRepository: NetworkMemberRepository
): ViewModel(){
    private val state:MutableState<HomeState> = mutableStateOf(HomeState())

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private fun updateUiState(user: User?, follower: List<Follower>?) {
        if(state.value.isMemberSuccess && state.value.isFollowSuccess) {
            _uiState.value = HomeUiState.Success(user?:User(), follower?: listOf())
            Log.d(TAG, follower.toString())
        } else {
            _uiState.value = HomeUiState.Error
        }
    }

    fun fetchDatas(navController: NavHostController) {
        val memberId = navController.previousBackStackEntry?.savedStateHandle
            ?.getLiveData<String>(NAVIGATE_LOGIN_KEY)?.value

        Log.d(TAG, memberId.toString())
        if(memberId != null){
            //fetchMemberInfo(memberId = memberId)
            val user = fetchUserInfo()
            if(user != null) {
                fetchFollowList()
            }
        }
    }


    private fun fetchUserInfo() {
        viewModelScope.launch {
            val result = authRepository.getUserInfo()
            var user: User? = null
            result.fold(
                onSuccess = {
                    user = it
                    Log.d(TAG, "user in: $it")

                    state.value.isMemberSuccess = true
                    state.value.user = user


                    fetchFollowList()
                },
                onFailure = {
                    Log.d(TAG, it.message.toString())
                }
            )
        }
    }

    private fun fetchFollowList() = viewModelScope.launch {
        val followList = followerRepository.fetchFollow()
        if(followList != null) {
            state.value.isFollowSuccess = true
            state.value.follower = followList

            updateUiState(user = state.value.user, follower = state.value.follower)
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
                val authRepository = application.appContainer.memberRepository
                HomeViewModel(
                    followerRepository = followRepository,
                    authRepository = authRepository)
            }
        }
    }
}