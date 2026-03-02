package com.example.frjarcustomer.core.di

import com.example.frjarcustomer.core.config.AppConfig
import com.example.frjarcustomer.core.config.BuildConfigProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigModule {

    @Binds
    @Singleton
    abstract fun bindAppConfig(impl: BuildConfigProvider): AppConfig
}
