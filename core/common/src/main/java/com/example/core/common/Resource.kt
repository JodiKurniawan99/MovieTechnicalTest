package com.example.core.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable, val message: String? = throwable.localizedMessage) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
