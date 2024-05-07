package com.sopt.now.compose.ui.screens.home

import com.sopt.now.compose.models.Follower
import com.sopt.now.compose.models.User

sealed interface HomeUiState {
    data class Success(
        val user: User,
        val follower: List<Follower>
    ) : HomeUiState
    data object Loading: HomeUiState
    data object Error: HomeUiState
}