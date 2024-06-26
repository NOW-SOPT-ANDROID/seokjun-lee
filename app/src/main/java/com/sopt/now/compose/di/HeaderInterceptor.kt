package com.sopt.now.compose.di

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
            name = MEMBER_ID,
            value = sharedPreferences.getString(MEMBER_ID, DEFAULT_VALUE).orEmpty()
        ).build()
        return chain.proceed(request)
    }

    companion object {
        const val MEMBER_ID = "memberId"
        const val DEFAULT_VALUE = ""
    }
}
