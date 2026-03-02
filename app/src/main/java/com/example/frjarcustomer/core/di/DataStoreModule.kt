package com.example.frjarcustomer.core.di

import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.AppDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindAppDataStore(impl: AppDataStoreImpl): AppDataStore
}
