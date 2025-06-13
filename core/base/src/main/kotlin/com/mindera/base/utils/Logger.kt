package com.mindera.base.utils

import android.util.Log

class Logger(private val tag: String) {

    fun d(message: String) {
        Log.d(tag, message)
    }

    fun i(message: String) {
        Log.i(tag, message)
    }

    fun e(message: String) {
        Log.e(tag, message)
    }

    fun e(message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
    }

}