package com.sopt.now.compose.container

import android.content.SharedPreferences
import com.sopt.now.compose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun getUserProfile(): User?
    suspend fun setUserProfile(user: User)
}

class PreferenceUserRepository(
    val sharedPreferences: SharedPreferences
) : UserRepository {
    override suspend fun getUserProfile(): User = withContext(Dispatchers.Main) {
        sharedPreferences.run {
            val id = getString(ID_KEY, "") ?: ""
            val pw = getString(PW_KEY, "") ?: ""
            val nickname = getString(NICKNAME_KEY, "") ?: ""
            val mbti = getString(MBTI_KEY, "") ?: ""
            return@withContext User(id, pw, nickname, mbti)
        }
    }

    override suspend fun setUserProfile(user: User) {
        val edit = sharedPreferences.edit()
        withContext(Dispatchers.Main) {
            edit.run {
                putString(ID_KEY, user.id)
                putString(PW_KEY, user.pw)
                putString(NICKNAME_KEY, user.nickName)
                putString(MBTI_KEY, user.mbti)
                commit()
            }
        }
    }

    companion object {
        const val ID_KEY = "id"
        const val PW_KEY = "pw"
        const val NICKNAME_KEY = "nickname"
        const val MBTI_KEY = "mbti"
    }
}