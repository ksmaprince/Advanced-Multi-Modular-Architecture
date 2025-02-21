package com.khun.login.data.source

import com.khun.data.error.toDomain
import com.khun.data.result.OutCome
import com.khun.data.source.NetworkDataSource
import com.khun.login.data.requests.LoginRequestBody
import com.khun.login.data.service.LoginService
import com.khun.login.domain.User
import com.khun.login.domain.model.LoginMapper

class LoginRemoteImpl(
  private val networkDataSource: NetworkDataSource<LoginService>,
  private val loginMapper: LoginMapper,
) : LoginRemote {
  override suspend fun login(loginRequestBody: LoginRequestBody): OutCome<User> {
    return networkDataSource.performRequest(
      request = { login(loginRequestBody).await() },
      onSuccess = { response, _ -> OutCome.success(loginMapper.toDomain(response)) },
      onError = { errorResponse, code -> OutCome.error(errorResponse.toDomain(code)) },
    )
  }
}
