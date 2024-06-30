package com.sopt.now.compose.presentation.screens.home

import com.sopt.now.compose.domain.entity.response.FollowerResponseEntity
import com.sopt.now.compose.domain.entity.response.UserResponseEntity

sealed interface FollowerUiState {
    data class Success(
        val follower: List<FollowerResponseEntity>
    ) : FollowerUiState
    data object Loading: FollowerUiState
    data class Error(
        val errorMessage: String
    ): FollowerUiState
}