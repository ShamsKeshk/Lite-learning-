package com.example.liteeducation.model

sealed class Result<out R> {

    data class Success<out T>(val data: T): Result<T>()
    data class Error<out T>(val error: Throwable): Result<T>()
    class Loading<out T> : Result<T>()
    data class LoadingProgress<out T>(val progress: Int) : Result<T>()

    fun isLoading(): Boolean = this is Loading

    fun isSuccessful(): Boolean = this is Success

    fun isFailed(): Boolean = this is Error

    fun getErrorMessage() : String {
        if (this is Error){
            return this.error?.message ?: " "
        }

        return " "
    }

}