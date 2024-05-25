package com.ersiver.test_krunal.utils

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    FAILED
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }

        fun <T> failed(error: String, data: T?): Resource<T> {
            return Resource(Status.FAILED, data, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

interface onApiResponse<T> {
    fun onLoading(msg: String)
    fun onSuccess(body: T?)
    fun onFailed(error: String)
    fun onError(error: Throwable?)
}
