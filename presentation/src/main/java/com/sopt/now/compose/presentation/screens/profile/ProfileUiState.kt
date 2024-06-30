package com.sopt.now.compose.presentation.screens.profile

import com.sopt.now.compose.domain.entity.response.UserResponseEntity

sealed interface ProfileUiState {
    data class Success(
        val authenticationId: String,
        val nickname: String,
        val phone: String
    ) : ProfileUiState
    data object Loading: ProfileUiState
    data object Error: ProfileUiState
}