package com.khun.login.data.di

import com.google.gson.Gson
import com.khun.data.connectivity.NetworkMonitorInterface
import com.khun.data.constants.DISPATCHER_DEFAULT_TAG
import com.khun.data.constants.USER_ID_TAG
import com.khun.data.factory.ServicFactory
import com.khun.data.source.NetworkDataSource
import com.khun.login.data.service.LoginService
import com.khun.login.data.source.LoginRemote
import com.khun.login.data.source.LoginRemoteImpl
import com.khun.login.domain.model.LoginMapper
import com.khun.login.domain.model.LoginMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideLoginServiceFactory(serviceFactory: ServicFactory): LoginService {
    return serviceFactory.create(LoginService::class.java)
  }

  @Provides
  @Singleton
  fun provideNetworkDataSource(
    loginService: LoginService,
    gson: Gson,
    networkMonitorInterface: NetworkMonitorInterface,
    @Named(USER_ID_TAG) userIdProvider: () -> String,
  ): NetworkDataSource<LoginService> {
    return NetworkDataSource(loginService, gson, networkMonitorInterface, userIdProvider)
  }

  @Provides
  @Singleton
  fun provideLoginMapper(
    @Named(DISPATCHER_DEFAULT_TAG) coroutineDispatcher: CoroutineDispatcher,
  ): LoginMapper {
    return LoginMapperImpl(coroutineDispatcher)
  }

  @Provides
  @Singleton
  fun provideLoginRemoteImplementer(
    networkDataSource: NetworkDataSource<LoginService>,
    loginMapper: LoginMapper,
  ): LoginRemote {
    return LoginRemoteImpl(networkDataSource, loginMapper)
  }
}
