package com.sopt.now.compose.ui.screens.profile

import com.sopt.now.compose.models.User

sealed interface ProfileUiState {
    data class Success(val user: User) : ProfileUiState
    data object Loading: ProfileUiState
    data object Error: ProfileUiState
}