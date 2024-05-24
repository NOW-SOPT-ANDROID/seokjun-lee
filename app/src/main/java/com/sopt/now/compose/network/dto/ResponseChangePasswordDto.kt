package com.sopt.now.compose.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseChangePasswordDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String
)