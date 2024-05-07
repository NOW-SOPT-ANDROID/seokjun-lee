package com.sopt.now.compose.ui.screens.login

data class LoginUiState(
    val id: String = "",
    val pw: String = "",
    val isSuccess: Boolean = false,
    val message:String = ""
)
