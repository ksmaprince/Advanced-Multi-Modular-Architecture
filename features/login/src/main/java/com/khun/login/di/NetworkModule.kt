package com.khun.login.di

import com.khun.data.factory.ServicFactory
import com.khun.login.service.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
  @Provides
  @Singleton
  fun provideLoginServiceFactory(serviceFactory: ServicFactory): LoginService {
    return serviceFactory.create(LoginService::class.java)
  }
}
