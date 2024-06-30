package com.sopt.now.compose.data.datasource

interface PreferenceDataSource {
    fun setUserId(userId: String)
    fun getUserId(): String
}