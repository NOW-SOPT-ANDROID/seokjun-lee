package com.sopt.now.main.adapter

import androidx.annotation.DrawableRes

sealed class ViewObject {
    data class UserViewObject(
        @DrawableRes
        val image: Int,
        val name: String,
        val description: String
    ): ViewObject()

    data class FriendViewObject(
        @DrawableRes
        val profileImage: Int,
        val name: String,
        val selfDescription: String
    ): ViewObject()
}