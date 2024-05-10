package com.sopt.now.main

import com.sopt.now.models.User

data class MainState(
    val isSuccess: Boolean,
    val message: String,
    val userData: User? = null
)