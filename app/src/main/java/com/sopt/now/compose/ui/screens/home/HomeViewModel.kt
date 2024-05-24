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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

class HomeViewModel(
    private val followerRepository: FollowerRepository,
    private val authRepository: NetworkMemberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private fun updateUiState(
        state: HomeUiState.Loading = _uiState.value as HomeUiState.Loading
    ) {
        if (state.isUserSuccess && state.isFollowerSuccess) {
            _uiState.value = HomeUiState.Success(
                user = state.user,
                follower = state.follower
            )
        } else {
            _uiState.value = HomeUiState.Error
        }
    }

    fun fetchNetworkData() {
        fetchUserInfo()
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            val result = authRepository.getUserInfo()
            result.fold(
                onSuccess = {
                    val state = _uiState.value as HomeUiState.Loading
                    _uiState.value = HomeUiState.Loading(
                        isUserSuccess = true,
                        user = it,
                        isFollowerSuccess = state.isFollowerSuccess,
                        follower = state.follower
                    )
                    fetchFollowers()
                },
                onFailure = {
                    Log.d(TAG, it.message.toString())
                }
            )
        }
    }

    private fun fetchFollowers() = viewModelScope.launch {
        followerRepository.getFollowers().fold(
            onSuccess = {
                val followers = it.body()?.data
                if(it.isSuccessful) {
                    val state = _uiState.value as HomeUiState.Loading
                    _uiState.value = HomeUiState.Loading(
                        isUserSuccess = state.isUserSuccess,
                        isFollowerSuccess = true,
                        user = state.user,
                        follower = followers.orEmpty()
                    )
                    updateUiState()
                } else {

                }
            },
            onFailure = {
                Log.d(TAG, it.message.toString())
            }
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SoptApplication)
                val followRepository = application.appContainer.followRepository
                val authRepository = application.appContainer.memberRepository
                HomeViewModel(
                    followerRepository = followRepository,
                    authRepository = authRepository
                )
            }
        }
    }
}