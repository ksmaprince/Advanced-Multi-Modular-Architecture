package com.khun.data.source

import com.google.gson.Gson
import com.khun.data.connectivity.NetworkMonitorInterface
import com.khun.data.constants.HEADER_LOCATION
import com.khun.data.error.getDefaultErrorResponse
import com.khun.data.error.getErrorResponse
import com.khun.data.error.toDomain
import com.khun.data.response.ErrorResponse
import com.khun.data.result.OutCome
import com.khun.data.source.DataSource.Companion.NO_INTERNET
import com.khun.data.source.DataSource.Companion.SEE_OTHERS
import com.khun.data.source.DataSource.Companion.SSL_PINNING
import com.khun.data.source.DataSource.Companion.TIMEOUT
import com.khun.data.source.DataSource.Companion.UNKNOWN
import kotlinx.coroutines.isActive
import okhttp3.Headers
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException
import kotlin.coroutines.coroutineContext

class NetworkDataSource<SERVICE>(
  private val service: SERVICE,
  private val gson: Gson,
  private val networkMonitorInterface: NetworkMonitorInterface,
  private val userIdProvider: () -> String,
) {
  suspend fun <R, T> performRequest(
    request: suspend SERVICE.(String) -> Response<R>,
    onSuccess: suspend (R, Headers) -> OutCome<T> = { _, _ -> OutCome.empty() },
    onRedirect: suspend (String, Int) -> OutCome<T> = { _, _ -> OutCome.empty() },
    onEmpty: suspend () -> OutCome<T> = { OutCome.empty() },
    onError: suspend (ErrorResponse, Int) -> OutCome<T> = { errorResponse, code ->
      OutCome.error(errorResponse.toDomain(code))
    },
  ): OutCome<T> {
    return if (networkMonitorInterface.hasConnectivity()) {
      try {
        val response = service.request(userIdProvider())
        val responseCode = response.code()
        val errorBody = response.errorBody()?.string()
        if (response.isSuccessful || responseCode == SEE_OTHERS) {
          val body = response.body()
          return if (body != null && body != Unit) {
            if (coroutineContext.isActive) {
              onSuccess(body, response.headers())
            } else {
              onEmpty()
            }
          } else {
            // its success but body equal to null or its empty "Unit"
            val location =
              response.headers()[HEADER_LOCATION] // Users/1212121212/Requests/12121821826182618
            if (location != null) {
              onRedirect(location, responseCode)
            } else {
              onEmpty()
            }
          }
        } else if (errorBody.isNullOrBlank()) {
          onError(getDefaultErrorResponse(), responseCode)
        } else {
          onError(getErrorResponse(gson, errorBody), responseCode)
        }
      } catch (e: Exception) {
        e.printStackTrace()
        val code = when (e) {
          is SocketTimeoutException -> {
            TIMEOUT
          }

          is UnknownHostException -> {
            NO_INTERNET
          }

          is SSLPeerUnverifiedException, is SSLHandshakeException -> {
            SSL_PINNING
          }

          else -> {
            UNKNOWN
          }
        }
        onError(getDefaultErrorResponse(), code)
      }
    } else {
      // NO INTERNET ERROR
      onError(getDefaultErrorResponse(), NO_INTERNET)
    }
  }
}
