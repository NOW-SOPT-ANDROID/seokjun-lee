package com.sopt.now.main

import com.sopt.now.main.adapter.CommonItem

data class FollowState(
    val isSuccess: Boolean,
    val message: String = "",
    val friendList: MutableList<CommonItem> = mutableListOf()
)