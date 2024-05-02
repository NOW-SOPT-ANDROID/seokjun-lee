package com.sopt.now.network.dto

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
    val data:List<Data>,
    @SerialName("support")
    val support: Support

){
    @Serializable
    data class Data(
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

    @Serializable
    data class Support(
        @SerialName("url")
        val url: String,
        @SerialName("text")
        val text: String
    )
}