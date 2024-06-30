package com.sopt.now.compose.presentation.ui.screens.login

data class LoginUiState(
    val id: String = "",
    val password: String = "",
    val isSuccess: Boolean = false,
    val message:String = ""
)
