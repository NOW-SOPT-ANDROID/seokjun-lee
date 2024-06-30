package com.sopt.now.compose.presentation.screens.home

import com.sopt.now.compose.domain.entity.response.FollowerResponseEntity
import com.sopt.now.compose.domain.entity.response.UserResponseEntity

sealed interface HomeUiState {
    data class Success(
        val isSuccess: Boolean,
        val authenticationId: String,
        val nickname: String,
        val phone: String
    ) : HomeUiState
    data object Loading: HomeUiState
    data class Error(
        val errorMessage: String
    ): HomeUiState
}