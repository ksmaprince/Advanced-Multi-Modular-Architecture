package com.khun.login.data.source

import com.khun.data.result.OutCome
import com.khun.login.data.requests.LoginRequestBody
import com.khun.login.domain.User

interface LoginRemote {
  suspend fun login(loginRequestBody: LoginRequestBody): OutCome<User>
}
