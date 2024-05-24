package com.sopt.now.compose.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestChangePasswordDto(
    @SerialName("previousPassword")
    val previousPassword: String,
    @SerialName("newPassword")
    val newPassword: String,
    @SerialName("newPasswordVerification")
    val newPasswordVerification: String
)