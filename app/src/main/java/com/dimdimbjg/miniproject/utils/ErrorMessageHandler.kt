package com.dimdimbjg.miniproject.utils

import retrofit2.HttpException
import java.net.UnknownHostException

object ErrorMessageHandler {
    fun generateErrorMessage(error: Throwable): String {
        return when (error) {
            is UnknownHostException -> "Network Error"
            is HttpException -> "HTTP Error"
            else -> error.message.toString()
        }
    }
}