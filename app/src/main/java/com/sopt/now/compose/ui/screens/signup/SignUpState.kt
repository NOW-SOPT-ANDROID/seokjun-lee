package com.sopt.now.compose.ui.screens.signup

import com.sopt.now.compose.models.User

data class SignUpState(

    var isSuccess: Boolean = false,
    var message: String = "",
    var authenticationId: String = "",
    var password: String = "",
    var nickName: String = "",
    var phone: String = "",
)