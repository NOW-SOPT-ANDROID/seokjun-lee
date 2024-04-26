package com.sopt.now.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class User(
    var id: String = "",
    var pw: String = "",
    var nickName: String = "",
    var mbti: String = ""
): Parcelable