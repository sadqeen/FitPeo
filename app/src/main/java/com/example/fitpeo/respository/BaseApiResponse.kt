package com.example.fitpeo.respository

import android.content.Context
import com.example.fitpeo.network.NetworkResult
import com.example.fitpeo.util.SharedPreferenceClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

abstract class BaseApiResponse {

    @Inject
    lateinit var sharedPreferenceClient: SharedPreferenceClient

    @Inject
    @Named("Application_Context")
    lateinit var appContext: Context

    suspend inline fun <reified T> safeApiCallWithErrorData(
        dispatcher: CoroutineDispatcher,
        noinline apiCall: suspend () -> T
    ): NetworkResult<T> {
        return withContext(dispatcher) {
            try {
                NetworkResult.success(apiCall.invoke())
            } catch (throwable: Throwable) {
                processForError(throwable, apiCall, true, dispatcher)
            }
        }
    }

    suspend inline fun <reified T> processForError(
        throwable: Throwable,
        noinline apiCall: suspend () -> T,
        readErrorBody: Boolean,
        dispatcher: CoroutineDispatcher
    ): NetworkResult<T> {
        return when (throwable) {
            is IOException -> NetworkResult.error(throwable, null)
            is HttpException -> {
                val code = throwable.code()
                if (readErrorBody) {
                    val error: T? = convertErrorBody(throwable, dispatcher)
                    NetworkResult.error(throwable, error)
                } else {
                    NetworkResult.error(throwable, null)
                }
            }
            is Exception -> NetworkResult.error(throwable, null)
            else -> NetworkResult.error(throwable, null)
        }
    }

    suspend inline fun <reified T> convertErrorBody(
        throwable: HttpException,
        dispatcher: CoroutineDispatcher
    ): T? {
        return withContext(dispatcher) {
            try {
                val data =
                    throwable.response()?.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                return@withContext Gson().fromJson(data, T::class.java)
            } catch (exception: Exception) {
                null
            }
        }
    }

}