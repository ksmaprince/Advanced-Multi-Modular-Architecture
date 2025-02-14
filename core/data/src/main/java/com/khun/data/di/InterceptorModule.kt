package com.khun.data.di

import com.khun.data.BuildConfig
import com.khun.data.OkHttpClientProvider
import com.khun.data.constants.HEADER_INTERCEPTOR_TAG
import com.khun.data.constants.LOGGING_INTERCEPTOR_TAG
import com.khun.data.interceptors.AUTHORIZATION_HEADER
import com.khun.data.interceptors.CLIENT_ID_HEADER
import com.khun.data.interceptors.HeaderInterceptor
import com.khun.data.okhttp.IOkHttpClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.Locale
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InterceptorModule {
  @Provides
  @Singleton
  @Named(HEADER_INTERCEPTOR_TAG)
  fun provideHeaderInterceptor(
    @Named("ClientId") clientId: String,
    @Named("AccessToken") accessTokenProvider: () -> String?,
    @Named("Language") languageProvider: () -> Locale,
  ): Interceptor {
    return HeaderInterceptor(
      clientId,
      accessTokenProvider,
      languageProvider,
    )
  }

  // Http Logging Interceptor
  @Provides
  @Singleton
  @Named(LOGGING_INTERCEPTOR_TAG)
  fun provideOkHttpLoggingInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor.Level.BODY
    } else {
      HttpLoggingInterceptor.Level.NONE
    }
    if (!BuildConfig.DEBUG) {
      interceptor.redactHeader(CLIENT_ID_HEADER) // redact any header that contains sensitive data.
      interceptor.redactHeader(AUTHORIZATION_HEADER) // redact any header that contains sensitive data.
    }
    return interceptor
  }

  @Provides
  @Singleton
  fun provideOkHttpClientInterceptor(): IOkHttpClientProvider {
    return OkHttpClientProvider()
  }
}
