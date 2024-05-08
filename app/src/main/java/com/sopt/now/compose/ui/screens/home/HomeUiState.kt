package com.sopt.now.compose.ui.screens.home

import com.sopt.now.compose.models.Follower
import com.sopt.now.compose.models.User

sealed interface HomeUiState {
    data class Success(
        val user: User,
        val follower: List<Follower>
    ) : HomeUiState
    data class Loading(
        val isUserSuccess: Boolean = false,
        val isFollowerSuccess: Boolean = false,
        var user: User = User(),
        var follower: List<Follower> = listOf()
    ): HomeUiState
    data object Error: HomeUiState
}