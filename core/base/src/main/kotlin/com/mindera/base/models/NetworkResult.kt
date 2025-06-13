package com.mindera.base.models

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Failure(val errorTypes: ErrorTypes) : NetworkResult<Nothing>()
}
