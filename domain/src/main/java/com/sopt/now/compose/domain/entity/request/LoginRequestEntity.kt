package com.sopt.now.compose.domain.entity.request

data class LoginRequestEntity (
    val authenticationId: String,
    val password: String
)