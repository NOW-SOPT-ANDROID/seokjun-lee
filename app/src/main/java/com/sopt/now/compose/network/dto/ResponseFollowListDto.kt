package com.sopt.now.compose.network.dto

import com.sopt.now.compose.models.Follower
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFollowListDto(
    @SerialName("page")
    val page:Int,
    @SerialName("per_page")
    val per_page:Int,
    @SerialName("total")
    val total:Int,
    @SerialName("total_pages")
    val total_pages:Int,
    @SerialName("data")
    val data:List<Follower>,
    @SerialName("support")
    val support: Support

){
    @Serializable
    data class Support(
        @SerialName("url")
        val url: String,
        @SerialName("text")
        val text: String
    )
}