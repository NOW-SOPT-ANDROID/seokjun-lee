package com.sopt.now.compose.domain.entity.response

data class UserResponseEntity(
    val authenticationId: String,
    val nickname: String,
    val phone: String
)