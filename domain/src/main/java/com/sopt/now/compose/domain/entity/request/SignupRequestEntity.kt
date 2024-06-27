package com.sopt.now.compose.domain.entity.request

data class SignupRequestEntity (
    val authenticationId: String,
    val password: String,
    val nickname: String,
    val phone: String
)