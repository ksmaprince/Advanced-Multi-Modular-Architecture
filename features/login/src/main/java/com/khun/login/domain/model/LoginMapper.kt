package com.khun.login.domain.model

import com.khun.login.data.responses.UserResponse
import com.khun.login.domain.User

interface LoginMapper {
  suspend fun toDomain(userResponse: UserResponse): User
}
