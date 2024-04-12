package com.sopt.now.models

import java.io.Serializable

data class User(
    var id: String = "",
    var pw: String = "",
    var nickName: String = "",
    var mbti: String = ""
): Serializable{}