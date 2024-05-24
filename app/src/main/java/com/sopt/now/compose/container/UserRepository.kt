package com.sopt.now.compose.container

import android.content.SharedPreferences
import android.util.Log
import com.sopt.now.compose.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun getUserProfile(): User?
    suspend fun setUserProfile(user: User)

    suspend fun getUserId(): String
    suspend fun setUserId(userId: String)
}

class PreferenceUserRepository(
    val sharedPreferences: SharedPreferences
) : UserRepository {
    override suspend fun getUserProfile(): User {
        sharedPreferences.run {
            val id = getString(ID_KEY, "") ?: ""
            val pw = getString(PW_KEY, "") ?: ""
            val nickname = getString(NICKNAME_KEY, "") ?: ""
            val mbti = getString(MBTI_KEY, "") ?: ""
            return User(id, pw, nickname, mbti)
        }
    }

    override suspend fun setUserProfile(user: User) {
        val edit = sharedPreferences.edit()
        edit.run {
            putString(ID_KEY, user.id)
            putString(PW_KEY, user.pw)
            putString(NICKNAME_KEY, user.nickName)
            putString(MBTI_KEY, user.phone)
            commit()
        }
    }

    override suspend fun getUserId(): String {
        return sharedPreferences.getString(USER_ID_KEY, "").orEmpty()
    }

    override suspend fun setUserId(userId: String) {
            Log.d(TAG, "userId: $userId")
            val edit = sharedPreferences.edit()
            edit.putString(USER_ID_KEY, userId).apply()

    }

    companion object {
        const val ID_KEY = "id"
        const val PW_KEY = "pw"
        const val NICKNAME_KEY = "nickname"
        const val MBTI_KEY = "mbti"

        const val USER_ID_KEY = "user_id"
        private const val TAG = "UserRepository"
    }
}