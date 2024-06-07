package com.sopt.now.compose.container.repository

import android.content.SharedPreferences
import android.util.Log
import com.sopt.now.compose.models.User

interface UserRepository {
    suspend fun getUserProfile(): User?
    suspend fun setUserProfile(user: User)

    suspend fun getUserId(): String
    suspend fun setUserId(userId: String)
}

