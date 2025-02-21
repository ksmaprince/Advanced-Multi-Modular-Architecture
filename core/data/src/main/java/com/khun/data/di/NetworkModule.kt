package com.khun.data.di

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.khun.data.BuildConfig
import com.khun.data.OkHttpClientProvider
import com.khun.data.connectivity.NetworkMonitorImplementer
import com.khun.data.connectivity.NetworkMonitorInterface
import com.khun.data.constants.HEADER_INTERCEPTOR_TAG
import com.khun.data.constants.LOGGING_INTERCEPTOR_TAG
import com.khun.data.factory.ServicFactory
import com.khun.data.okhttp.IOkHttpClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideGson(): Gson {
    return Gson()
  }

  @Provides
  @Singleton
  fun provideNetworkMonitor(context: Context): NetworkMonitorInterface {
    return NetworkMonitorImplementer(context)
  }

  @Provides
  @Singleton
  fun provideOkHttpClientInterceptor(): IOkHttpClientProvider {
    return OkHttpClientProvider()
  }

  // ok http factory
  @Provides
  @Singleton
  fun provideOkHttpCallFactory(
    @Named(LOGGING_INTERCEPTOR_TAG) okHttpLoggingInterceptor: Interceptor,
    @Named(HEADER_INTERCEPTOR_TAG) interceptor: Interceptor,
    okHttpClientProvider: IOkHttpClientProvider,
  ): OkHttpClient {
    return okHttpClientProvider.getOkHttpClient(BuildConfig.PIN_CERTIFICATE)
      .addInterceptor(okHttpLoggingInterceptor)
      .addInterceptor(interceptor)
      .retryOnConnectionFailure(true)
      .followRedirects(false)
      .followSslRedirects(false)
      .connectTimeout(60, TimeUnit.SECONDS)
      .readTimeout(60, TimeUnit.SECONDS)
      .writeTimeout(60, TimeUnit.SECONDS)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
      .baseUrl("")
      .client(okHttpClient)
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
    return builder.build()
  }

  @Provides
  @Singleton
  fun provideServiceFactory(retrofit: Retrofit): ServicFactory {
    return ServicFactory(retrofit)
  }
}
