package com.khun.data.okhttp

import okhttp3.OkHttpClient

interface IOkHttpClientProvider {
  fun getOkHttpClient(pin: String): OkHttpClient.Builder
  fun cancelAllRequests()
}
