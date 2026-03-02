package com.example.frjarcustomer.data.remote.di

import com.example.frjarcustomer.data.remote.repository.Repository
import com.example.frjarcustomer.data.remote.repository.RepositoryImp
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
    abstract fun bindRepository(impl: RepositoryImp): Repository
}