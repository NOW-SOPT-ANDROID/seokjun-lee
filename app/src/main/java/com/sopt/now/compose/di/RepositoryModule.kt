package com.sopt.now.compose.di

import com.sopt.now.compose.container.impl.AuthRepositoryImpl
import com.sopt.now.compose.container.impl.FollowerRepositoryImpl
import com.sopt.now.compose.container.impl.MemberRepositoryImpl
import com.sopt.now.compose.container.impl.UserRepositoryImpl
import com.sopt.now.compose.container.repository.AuthRepository
import com.sopt.now.compose.container.repository.FollowerRepository
import com.sopt.now.compose.container.repository.MemberRepository
import com.sopt.now.compose.container.repository.UserRepository
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
    abstract fun bindMemberRepository(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}