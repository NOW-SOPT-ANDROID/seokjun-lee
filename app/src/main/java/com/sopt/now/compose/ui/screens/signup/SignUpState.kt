package com.sopt.now.compose.ui.screens.signup

data class SignUpState(
    val isSuccess: Boolean = false,
    val message: String = "",
    val authenticationId: String = "",
    val password: String = "",
    val nickName: String = "",
    val phone: String = "",
)