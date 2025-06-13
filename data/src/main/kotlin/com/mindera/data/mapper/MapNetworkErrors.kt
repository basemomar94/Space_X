package com.mindera.data.mapper

import android.os.Build
import androidx.annotation.RequiresExtension
import com.mindera.base.models.ErrorTypes
import java.io.IOException
import retrofit2.HttpException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun Throwable.mapThrowable() = when (this) {
    is IOException -> ErrorTypes.IoException
    is HttpException -> ErrorTypes.Http(this.code(), this.message)
    else -> ErrorTypes.Unknown(this.message)
}