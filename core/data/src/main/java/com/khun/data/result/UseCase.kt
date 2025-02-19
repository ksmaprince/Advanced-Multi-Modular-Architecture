package com.khun.data.result

import com.khun.data.model.ErrorMessage

interface UseCase<R> {
  suspend fun onSuccess(success: OutCome.Success<R>)

  suspend fun onEmpty()

  suspend fun onError(errorMessage: ErrorMessage)
}
