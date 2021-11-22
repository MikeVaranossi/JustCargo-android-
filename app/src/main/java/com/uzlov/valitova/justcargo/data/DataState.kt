package com.uzlov.valitova.justcargo.data

sealed class DataState<out T: Any> {
    data class Success<T : Any>(val data: List<T>) : DataState<T>()
    data class Loading<T: Any>(val progress: Int): DataState<T>()
    data class Error<T: Any>(val error: Throwable): DataState<T>()
}