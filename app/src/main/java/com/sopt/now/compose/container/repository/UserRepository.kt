package com.sopt.now.compose.container.repository

import android.content.SharedPreferences
import android.util.Log
import com.sopt.now.compose.models.User

interface UserRepository {
    fun getUserProfile(): User?
    fun setUserProfile(user: User)
    fun getUserId(): String
    fun setUserId(userId: String)
}

