package com.example.frjarcustomer.core.di

import com.example.frjarcustomer.core.fcm.FcmRepository
import com.example.frjarcustomer.data.remote.repository.fcm.FcmRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FcmRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFcmRepository(impl: FcmRepositoryImpl): FcmRepository
}
