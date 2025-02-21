package com.khun.login.domain.model

import com.khun.login.data.responses.UserResponse
import com.khun.login.domain.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoginMapperImpl(private val defaultDispatcher: CoroutineDispatcher) : LoginMapper {
  override suspend fun toDomain(userResponse: UserResponse): User {
    return withContext(defaultDispatcher) {
      User(
        id = userResponse.id.orEmpty(),
        fullName = userResponse.fullName.orEmpty(),
        email = userResponse.email.orEmpty(),
        photo = userResponse.photo.orEmpty(),
      )
    }
  }
}
