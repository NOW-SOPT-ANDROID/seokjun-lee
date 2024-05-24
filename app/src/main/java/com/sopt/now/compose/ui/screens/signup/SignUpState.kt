package com.sopt.now.compose.ui.screens.signup

import com.sopt.now.compose.models.User

data class SignUpState(
    val isSuccess: Boolean = false,
    val message: String = "",
    val authenticationId: String = "",
    val password: String = "",
    val nickName: String = "",
    val phone: String = "",
)