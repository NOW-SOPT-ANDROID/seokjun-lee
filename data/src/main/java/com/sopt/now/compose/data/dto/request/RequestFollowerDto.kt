package com.sopt.now.compose.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestFollowerDto (
    @SerialName("page")
    val page: Int
)