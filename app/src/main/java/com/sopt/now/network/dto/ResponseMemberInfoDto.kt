package com.sopt.now.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMemberInfoDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message:String,
    @SerialName("data")
    val data: Data
) {
    @Serializable
    data class Data(
        @SerialName("authenticationId")
        val authenticationId: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("phone")
        val phone: String
    )
}
