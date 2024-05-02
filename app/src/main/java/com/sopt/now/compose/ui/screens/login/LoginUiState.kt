package com.sopt.now.compose.ui.screens.login

data class LoginUiState(
    var id: String = "",
    var pw: String = "",
    var userIndex: Int = -1,
    var isSuccess: Boolean = false,
    var message:String = ""
)
