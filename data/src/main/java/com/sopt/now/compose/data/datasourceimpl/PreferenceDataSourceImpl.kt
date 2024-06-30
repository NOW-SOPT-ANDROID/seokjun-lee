package com.sopt.now.compose.data.datasourceimpl

import android.content.SharedPreferences
import com.sopt.now.compose.data.datasource.PreferenceDataSource
import javax.inject.Inject

class PreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreferenceDataSource {
    override fun setUserId(userId: String) {
        sharedPreferences.edit().putString(USER_ID, userId).apply()
    }

    override fun getUserId(): String = sharedPreferences.getString(USER_ID, DEFAULT_VALUE).orEmpty()


    companion object {
        private const val USER_ID = "userId"
        private const val DEFAULT_VALUE = ""
    }
}