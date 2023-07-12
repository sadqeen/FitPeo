package com.example.fitpeo.network

data class NetworkResult<out T>(
    var status: Status,
    val data: T?,
    val throwable: Throwable?
) {
    companion object {
        fun <T> success(data: T?): NetworkResult<T> {
            return NetworkResult(status = Status.SUCCESS, data = data, throwable = null)
        }

        fun <T> error(msg: Throwable? = null, data: T? = null): NetworkResult<T> {
            return NetworkResult(status = Status.ERROR, data = data, throwable = msg)
        }

        fun <T> loading(data: T? = null): NetworkResult<T> {
            return NetworkResult(status = Status.LOADING, data = data, throwable = null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}
