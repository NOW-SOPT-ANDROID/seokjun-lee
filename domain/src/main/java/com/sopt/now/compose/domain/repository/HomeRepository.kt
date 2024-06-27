package com.sopt.now.compose.domain.repository

import com.sopt.now.compose.domain.entity.response.UserResponseEntity

interface HomeRepository {
    suspend fun getUser(): Result<UserResponseEntity>
}