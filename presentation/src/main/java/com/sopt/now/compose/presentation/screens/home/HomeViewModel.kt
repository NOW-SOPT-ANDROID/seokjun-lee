package com.sopt.now.compose.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.domain.entity.request.FollowerRequestEntity
import com.sopt.now.compose.domain.repository.FollowerRepository
import com.sopt.now.compose.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val followerRepository: FollowerRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    private val _uiStateFollower = MutableStateFlow<FollowerUiState>(FollowerUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    val uiStateFollower: StateFlow<FollowerUiState> = _uiStateFollower.asStateFlow()

    fun getNetworkData() {
        //getUserInfo()
        _uiState.value = HomeUiState.Success(
            isSuccess = true,
            authenticationId = "seokjun",
            nickname = "seokjun",
            phone = "010-0000-0000"
        )
        getFollowerList()
    }

    private fun getUserInfo() = viewModelScope.launch {
            homeRepository.getUser().fold(
                onSuccess = {
                    _uiState.value = HomeUiState.Success(
                        isSuccess = true,
                        authenticationId = it.authenticationId,
                        nickname = it.nickname,
                        phone = it.phone
                    )
                },
                onFailure = {
                    _uiState.value = HomeUiState.Error(
                        errorMessage = it.message.toString()
                    )
                }
            )
        }

    private fun getFollowerList() = viewModelScope.launch {
        Log.d("HomeViewModel", "getFollowerList()")
        followerRepository.getFollowers(
            FollowerRequestEntity(page = 2)
        ).fold(
            onSuccess = {
                _uiStateFollower.value = FollowerUiState.Success(
                    follower = it
                )
                Log.d("HomeViewModel", "successful call")
            },
            onFailure = {
                _uiState.value = HomeUiState.Error(
                    errorMessage = it.message.toString()
                )
                Log.d("HomeViewModel", "error: ${it.message.toString()}")
            }
        )
    }
}