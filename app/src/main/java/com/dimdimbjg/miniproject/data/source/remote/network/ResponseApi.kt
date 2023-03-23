package com.dimdimbjg.miniproject.data.source.remote.network

sealed class ResponseApi<out R> {
    data class Success<out T>(val data: T) : ResponseApi<T>()
    data class Error(val errorMessage: String) : ResponseApi<Nothing>()
    object Empty : ResponseApi<Nothing>()
}