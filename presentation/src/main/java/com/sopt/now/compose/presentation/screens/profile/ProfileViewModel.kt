package com.sopt.now.compose.presentation.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.domain.entity.response.UserResponseEntity
import com.sopt.now.compose.domain.repository.HomeRepository
import com.sopt.now.compose.presentation.screens.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun getUserInfo() {
        _uiState.value = ProfileUiState.Success(
            authenticationId = "seokjun",
            nickname = "seokjun",
            phone = "010-0000-0000"
        )
        /*viewModelScope.launch {
            val result = homeRepository.getUser()
            result.fold(
                onSuccess = {
                    _uiState.value = ProfileUiState.Success(
                        authenticationId = it.authenticationId,
                        nickname = it.nickname,
                        phone = it.phone"
                    )
                },
                onFailure = {
                    Log.d(TAG, it.message.toString())
                }
            )
        }*/
    }

    companion object{
        private const val TAG = "ProfileViewModel"
    }
}