package com.sopt.now.compose.di

import com.sopt.now.compose.data.service.AuthService
import com.sopt.now.compose.data.service.FollowerService
import com.sopt.now.compose.data.service.HomeService
import com.sopt.now.compose.di.qualifier.AUTH
import com.sopt.now.compose.di.qualifier.HEADER
import com.sopt.now.compose.di.qualifier.REQRES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(@AUTH retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideHomeService(@HEADER retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideReqresService(@REQRES retrofit: Retrofit): FollowerService =
        retrofit.create(FollowerService::class.java)

}