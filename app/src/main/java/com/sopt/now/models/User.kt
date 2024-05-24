package com.sopt.now.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var pw: String = "",
    var nickName: String = "",
    var phoneNum: String = ""
): Parcelable