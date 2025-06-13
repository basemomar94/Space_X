package com.mindera.base.models

sealed class ErrorTypes {
    object IoException : ErrorTypes()
    data class Http(val code: Int, val message: String?) : ErrorTypes()
    data class Unknown(val reason: String?) : ErrorTypes()
}