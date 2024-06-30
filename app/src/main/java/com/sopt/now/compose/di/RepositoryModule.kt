package com.sopt.now.compose.di


import com.sopt.now.compose.data.repositoryimpl.AuthRepositoryImpl
import com.sopt.now.compose.data.repositoryimpl.FollowerRepositoryImpl
import com.sopt.now.compose.data.repositoryimpl.HomeRepositoryImpl
import com.sopt.now.compose.domain.repository.AuthRepository
import com.sopt.now.compose.domain.repository.FollowerRepository
import com.sopt.now.compose.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindFollowRepository(followerRepositoryImpl: FollowerRepositoryImpl): FollowerRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}