package com.sopt.now.compose.ui.screens.home

import com.sopt.now.compose.models.Follow
import com.sopt.now.compose.models.User

sealed interface HomeUiState {
    data class Success(
        var user: User,
        var follow: List<Follow>
    ) : HomeUiState
    data object Loading: HomeUiState
    data object Error: HomeUiState
}