package com.sopt.now.feat

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
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
    private lateinit var preferenceEditor: Editor

    init {
        sharedPreferences = context.getSharedPreferences(DATABASE_PREF_KEY, Context.MODE_PRIVATE)
    }

    fun setProfile(user: User) {
        preferenceEditor = sharedPreferences.edit()
        preferenceEditor.putString(ID_PREF_KEY, user.id)
        preferenceEditor.putString(PW_PREF_KEY, user.pw)
        preferenceEditor.putString(NICKNAME_PREF_KEY, user.nickName)
        preferenceEditor.putString(MBTI_PREF_KEY, user.mbti)
        preferenceEditor.commit()
    }

    fun getProfile(): User? {
        return if(sharedPreferences.contains(ID_PREF_KEY) && sharedPreferences.contains(PW_PREF_KEY)) {
                User(
                    id = sharedPreferences.getString(ID_PREF_KEY, null)?:"",
                    pw = sharedPreferences.getString(PW_PREF_KEY, null)?:"",
                    nickName = sharedPreferences.getString(NICKNAME_PREF_KEY, null)?:"",
                    mbti = sharedPreferences.getString(MBTI_PREF_KEY, null)?:"",
                )
        } else null
    }
}