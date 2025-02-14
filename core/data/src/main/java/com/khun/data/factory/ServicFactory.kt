package com.khun.data.factory

import retrofit2.Retrofit

class ServicFactory(private val retrofit: Retrofit) {
  fun <T> create(service: Class<T>): T {
    return retrofit.create(service)
  }
}
