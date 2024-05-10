package com.sopt.now.network.dto

import com.sopt.now.R
import com.sopt.now.main.adapter.CommonItem
import com.sopt.now.main.adapter.CommonViewType
import com.sopt.now.main.adapter.ViewObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFollowListDto(
    @SerialName("page")
    val page:Int,
    @SerialName("per_page")
    val perPage:Int,
    @SerialName("total")
    val total:Int,
    @SerialName("total_pages")
    val totalPages:Int,
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

fun convertDataListToCommonItems(dataList: List<ResponseFollowListDto.Data>): MutableList<CommonItem> {
    val itemList = mutableListOf<CommonItem>()
    for (data in dataList) {
        itemList.add(
            CommonItem(
                viewType = CommonViewType.FRIEND_VIEW.name,
                viewObject = ViewObject.FriendViewObject(
                    profileImage = R.drawable.ic_launcher_foreground,
                    name = data.firstName + " " + data.lastName,
                    selfDescription = data.email,
                    imageUrl = data.avatar
                )
            )
        )
    }
    return itemList
}