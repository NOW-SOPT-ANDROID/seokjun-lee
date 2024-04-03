package com.sopt.now.compose.models

import java.io.Serializable

data class User(
    var id: String = "",
    var pw: String = "",
    var nickName: String = "",
    var mbti: String = "",
): Serializable
