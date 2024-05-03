package com.sopt.now.compose.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Follow(
    @SerialName("id")
    val id:Int,
    @SerialName("email")
    val email:String,
    @SerialName("first_name")
    val firstName:String,
    @SerialName("last_name")
    val lastName:String,
    @SerialName("avatar")
    val avatar:String
)
