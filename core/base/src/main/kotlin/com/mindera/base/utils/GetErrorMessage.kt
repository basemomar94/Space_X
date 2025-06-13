package com.mindera.base.utils

import android.content.Context
import com.mindera.base.models.ErrorTypes
import com.mindera.rocketscience.base.R

fun Context.getErrorMessage(errorType: ErrorTypes): String = when (errorType) {
    is ErrorTypes.Unknown -> errorType.reason ?: getString(R.string.unexpected_error)
    ErrorTypes.IoException -> getString(R.string.net_work_error)
    is ErrorTypes.Http -> when (errorType.code) {
        400 -> getString(R.string.bad_request)
        401 -> getString(R.string.unauthorized)
        403 -> getString(R.string.forbidden)
        404 -> getString(R.string.not_found)
        in 500..599 -> getString(R.string.server_error)
        else -> getString(R.string.http_error_generic, errorType.code)
    }
}
