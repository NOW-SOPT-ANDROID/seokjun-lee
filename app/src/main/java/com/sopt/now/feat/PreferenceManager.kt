package com.sopt.now.feat

import android.content.Context
import android.content.SharedPreferences
import com.sopt.now.models.User

class PreferenceManager(context: Context) {
    companion object{
        const val DATABASE_PREF_KEY = "SOPT"
        const val ID_PREF_KEY = "ID"
        const val PW_PREF_KEY = "PW"
        const val NICKNAME_PREF_KEY = "NICKNAME"
        const val MBTI_PREF_KEY = "MBTI"
    }
    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(DATABASE_PREF_KEY, Context.MODE_PRIVATE)
    }

    fun setProfile(user: User) {
        with(sharedPreferences.edit()) {
            putString(ID_PREF_KEY, user.id)
            putString(PW_PREF_KEY, user.pw)
            putString(NICKNAME_PREF_KEY, user.nickName)
            putString(MBTI_PREF_KEY, user.phoneNum)
            commit()
        }
    }

    fun getProfile(): User? {
        return if(sharedPreferences.contains(ID_PREF_KEY) && sharedPreferences.contains(PW_PREF_KEY)) {
            with(sharedPreferences){
                User(
                    id = getString(ID_PREF_KEY, null)?:"",
                    pw = getString(PW_PREF_KEY, null)?:"",
                    nickName = getString(NICKNAME_PREF_KEY, null)?:"",
                    phoneNum = getString(MBTI_PREF_KEY, null)?:"",
                )
            }

        } else null
    }
}